package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.Activity;
import com.emrecalik.swe573.server.domain.Article;
import com.emrecalik.swe573.server.domain.User;
import com.emrecalik.swe573.server.domain.WikiItem;
import com.emrecalik.swe573.server.exception.ResourceNotFoundException;
import com.emrecalik.swe573.server.model.request.ArticlePostRequestDto;
import com.emrecalik.swe573.server.model.response.ApiResponseDto;
import com.emrecalik.swe573.server.model.response.ArticleResponseDto;
import com.emrecalik.swe573.server.model.response.PureArticleResponseDto;
import com.emrecalik.swe573.server.model.response.WikiApiResponseDto;
import com.emrecalik.swe573.server.repository.ArticleRepository;
import com.emrecalik.swe573.server.service.mapper.ArticleMapper;
import com.emrecalik.swe573.server.service.mapper.WikiItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final String SUCCESS = "Success";

    private static final String ARTICLE_SUCCESSFULLY_TAGGED = "Article(s) is(are) successfully tagged.";

    private static final String ARTICLE_SUCCESSFULLY_DELETED = "Article is successfully deleted.";

    private static final String ARTICLE_TAG_SUCCESSFULLY_DELETED = "Article Tag is successfully deleted.";

    private static final String ARTICLE_COULD_NOT_BE_FOUND = "Article could not be found!";

    private static final String WIKI_ITEM_COULD_NOT_FOUND = "Wiki Item could not be found!";

    private final ArticleRepository articleRepository;

    private final UserService userService;

    private final WikiItemService wikiItemService;

    private final ActivityService activityService;

    public ArticleServiceImpl(ArticleRepository articleRepository,
                              UserService userService,
                              WikiItemService wikiItemService,
                              ActivityService activityService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.wikiItemService = wikiItemService;
        this.activityService = activityService;
    }

    @Override
    public ApiResponseDto saveArticle(ArticlePostRequestDto articlePostRequestDto) {

        Set<WikiItem> wikiItemSet = new HashSet<>();
        for (WikiApiResponseDto wikiApiResponseDto : articlePostRequestDto.getWikiItems()) {
            String entityId = wikiApiResponseDto.getEntityId();
            if (wikiItemService.existsByEntityId(entityId)) {
                wikiItemSet.add(wikiItemService.getByEntityId(entityId));
            } else {
                wikiItemSet.add(WikiItemMapper.convertWikiItemApiDtoToWikiItem(wikiApiResponseDto));
            }
        }

        Set<Article> articleSet = new HashSet<>();
        for (PureArticleResponseDto pureArticleResponseDto : articlePostRequestDto.getPureArticles()) {
            articleSet.add(ArticleMapper.convertPureArticleResponseDtoToArticle(pureArticleResponseDto));
        }

        Long userId = articlePostRequestDto.getUserId();
        for (Article article : articleSet) {
            Long entityId = article.getEntityId();
            Article articleSaved;
            if (existsByEntityIdAndUserId(entityId, userId)) {
                Article articleInDb = getArticleByEntityIdAndUserId(entityId, userId);
                articleInDb.getWikiItems().addAll(wikiItemSet);
                articleSaved = articleRepository.save(articleInDb);
            } else {
                article.setWikiItems(wikiItemSet);
                User userProxy = userService.getUserProxyById(userId);
                article.setUser(userProxy);
                articleSaved = articleRepository.save(article);
            }

            Activity activity = Activity.builder()
                    .summary(" tagged ")
                    .activityType(Activity.ActivityType.TAG)
                    .objectType(Activity.ObjectType.ARTICLE)
                    .actorId(userId)
                    .objectId(articleSaved.getId())
                    .build();
            activityService.saveActivity(activity);
        }
        log.info("Article(s) have been saved.");

        return ApiResponseDto.builder()
                .header(ArticleServiceImpl.SUCCESS)
                .message(ArticleServiceImpl.ARTICLE_SUCCESSFULLY_TAGGED)
                .build();
    }

    @Override
    public boolean existsByEntityIdAndUserId(Long entityId, Long userId) {
        return articleRepository.existsByEntityIdAndUserId(entityId, userId);
    }

    @Override
    public Article getArticleByEntityIdAndUserId(Long entityId, Long userId) {
        return articleRepository.findByEntityIdAndUserId(entityId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(ArticleServiceImpl.ARTICLE_COULD_NOT_BE_FOUND));
    }

    @Override
    public Map<String, Object> getArticlesByUserId(Long userId, int pageNum) {
        int PAGE_SIZE = 5;
        Pageable pageWithFiveElements = PageRequest.of(pageNum - 1, PAGE_SIZE);
        Page<Article> articlePage = articleRepository.findAllByUserId(userId, pageWithFiveElements);
        return createPaginatedResponse(articlePage, userId);
    }

    @Transactional
    @Override
    public ApiResponseDto deleteArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ArticleServiceImpl.ARTICLE_COULD_NOT_BE_FOUND));

        Set<WikiItem> articleWikiItems = article.getWikiItems();

        for (WikiItem wikiItem : articleWikiItems) {
            if (wikiItemService.getById(wikiItem.getId()).getArticleSet().size() == 1) {
                wikiItemService.deleteWikiItem(wikiItem);
            }
        }

        articleRepository.delete(article);
        log.info("Article has been deleted.");

        activityService.deleteActivityByObjectId(id);

        return ApiResponseDto.builder()
                .header(ArticleServiceImpl.SUCCESS)
                .message(ArticleServiceImpl.ARTICLE_SUCCESSFULLY_DELETED)
                .build();
    }

    @Override
    public ArticleResponseDto getUserArticleById(Long id, Long userId) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ArticleServiceImpl.ARTICLE_COULD_NOT_BE_FOUND));
        return ArticleMapper.convertArticleToArticleResponseDto(article, userId);
    }

    @Override
    public Map<String, Object> getPaginatedArticles(int pageNum, Long userId) {
        int PAGE_SIZE = 5;
        Pageable pageWithFiveElements = PageRequest.of(pageNum - 1, PAGE_SIZE);
        Page<Article> articlePage = articleRepository.findAll(pageWithFiveElements);
        return createPaginatedResponse(articlePage, userId);
    }

    private Map<String, Object> createPaginatedResponse(Page<Article> articlePage, Long userId) {
        Set<ArticleResponseDto> articleResponseDtoSet = articlePage
                .getContent()
                .stream()
                .map(article -> ArticleMapper.convertArticleToArticleResponseDto(article, userId))
                .collect(Collectors.toSet());
        Map<String, Object> response = new HashMap<>();
        response.put("totalArticleCount", articlePage.getTotalElements());
        response.put("articles", articleResponseDtoSet);
        return response;
    }

    @Override
    public Article getArticleProxyById(Long articleId) {
        return articleRepository.getOne(articleId);
    }

    @Override
    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_COULD_NOT_BE_FOUND));
    }

    @Override
    public ApiResponseDto deleteArticleTag(Long articleId, String wikiItemEntityId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ArticleServiceImpl.ARTICLE_COULD_NOT_BE_FOUND));

        WikiItem articleWikiItem = article.getWikiItems().stream()
                .filter(wikiItem -> wikiItem.getEntityId().equals(wikiItemEntityId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(ArticleServiceImpl.WIKI_ITEM_COULD_NOT_FOUND));

        article.getWikiItems().remove(articleWikiItem);
        articleRepository.save(article);

        if (articleWikiItem.getArticleSet().isEmpty()) {
            wikiItemService.deleteWikiItem(articleWikiItem);
        }

        log.info("Article Tag: " + wikiItemEntityId + "  has been deleted.");

        Activity activity = Activity.builder()
                .summary(" updated ")
                .activityType(Activity.ActivityType.UPDATE)
                .objectType(Activity.ObjectType.ARTICLE)
                .actorId(article.getUser().getId())
                .objectId(articleId)
                .build();
        activityService.saveActivity(activity);

        return new ApiResponseDto(ArticleServiceImpl.SUCCESS, ArticleServiceImpl.ARTICLE_TAG_SUCCESSFULLY_DELETED);
    }
}
