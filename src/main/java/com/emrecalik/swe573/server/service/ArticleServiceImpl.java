package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.model.response.ApiResponseDto;
import com.emrecalik.swe573.server.model.request.ArticlePostRequestDto;
import com.emrecalik.swe573.server.model.response.ArticleResponseDto;
import com.emrecalik.swe573.server.model.response.EntrezApiResponseDto;
import com.emrecalik.swe573.server.model.response.WikiApiResponseDto;
import com.emrecalik.swe573.server.domain.Article;
import com.emrecalik.swe573.server.domain.User;
import com.emrecalik.swe573.server.domain.WikiItem;
import com.emrecalik.swe573.server.exception.ResourceNotFoundException;
import com.emrecalik.swe573.server.repository.ArticleRepository;
import com.emrecalik.swe573.server.service.mapper.ArticleMapper;
import com.emrecalik.swe573.server.service.mapper.WikiItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    private static final String ARTICLE_COULD_NOT_BE_FOUND= "Article could not be found!";

    private ArticleRepository articleRepository;

    private UserService userService;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    @Override
    public ApiResponseDto saveArticle(ArticlePostRequestDto articlePostRequestDto) {

        Set<WikiItem> wikiItemSet = new HashSet<>();
        for (WikiApiResponseDto wikiApiResponseDto : articlePostRequestDto.getWikiItems()) {
            wikiItemSet.add(WikiItemMapper.convertWikiItemApiDtoToWikiItem(wikiApiResponseDto));
        }

        Set<Article> articleSet = new HashSet<>();
        for (EntrezApiResponseDto entrezApiResponseDto : articlePostRequestDto.getArticles()) {
            articleSet.add(ArticleMapper.convertArticleApiDtoToArticle(entrezApiResponseDto));
        }

        User userProxy = userService.getUserProxy(articlePostRequestDto.getUserId());
        for (Article article : articleSet) {
            article.setWikiItems(wikiItemSet);
            article.setUser(userProxy);
        }

        articleRepository.saveAll(articleSet);
        log.info("Articles have been saved.");
        return ApiResponseDto.builder()
                .header(ArticleServiceImpl.SUCCESS)
                .message(ArticleServiceImpl.ARTICLE_SUCCESSFULLY_TAGGED)
                .build();
    }

    @Override
    public Map<String, Object> getArticlesByUserId(Long userId, int pageNum) {
        int PAGE_SIZE = 5;
        Pageable pageWithFiveElements = PageRequest.of(pageNum - 1, PAGE_SIZE);
        Page<Article> articlePage = articleRepository.findAllByUserId(userId, pageWithFiveElements);
        return createPaginatedResponse(articlePage, userId);
    }

    @Override
    public ApiResponseDto deleteArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ArticleServiceImpl.ARTICLE_COULD_NOT_BE_FOUND));
        articleRepository.delete(article);
        log.info("Article has been deleted.");
        return new ApiResponseDto(ArticleServiceImpl.SUCCESS, ArticleServiceImpl.ARTICLE_SUCCESSFULLY_DELETED);
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
    public Article getArticleProxy(Long articleId) {
        return articleRepository.getOne(articleId);
    }

    @Override
    public ApiResponseDto deleteArticleTag(Long articleId, String wikiItemEntityId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ArticleServiceImpl.ARTICLE_COULD_NOT_BE_FOUND));
        article.getWikiItems().removeIf(wikiItem -> wikiItem.getEntityId().equals(wikiItemEntityId));
        articleRepository.save(article);
        log.info("Article Tag: " + wikiItemEntityId + "  has been deleted.");

        return new ApiResponseDto(ArticleServiceImpl.SUCCESS, ArticleServiceImpl.ARTICLE_TAG_SUCCESSFULLY_DELETED);
    }
}
