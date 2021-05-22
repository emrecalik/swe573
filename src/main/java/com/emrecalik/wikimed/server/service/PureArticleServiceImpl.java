package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.purearticle.PureArticle;
import com.emrecalik.wikimed.server.exception.ResourceNotFoundException;
import com.emrecalik.wikimed.server.model.response.PureArticleResponseDto;
import com.emrecalik.wikimed.server.repository.PureArticleRepository;
import com.emrecalik.wikimed.server.service.mapper.ArticleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PureArticleServiceImpl implements PureArticleService {

    private static final String PURE_ARTICLE_COULD_NOT_BE_FOUND_FOR_ID = "Pure article could not be found for id = ";

    private static final String NO_RELATED_ARTICLE_FOR_KEYWORD = "No related article for keyword = ";

    private final PureArticleRepository pureArticleRepository;

    public PureArticleServiceImpl(PureArticleRepository pureArticleRepository) {
        this.pureArticleRepository = pureArticleRepository;
    }

    @Override
    public Map<String, Object> queryPureArticles(String query, int pageNum) {
        final int PAGE_SIZE = 10;
        Pageable pageWithTenArticles = PageRequest.of(pageNum - 1, PAGE_SIZE);
        Page<PureArticle> articlePage = pureArticleRepository.findPageByKeyword(query, pageWithTenArticles);

        if (articlePage.isEmpty()) {
            throw new ResourceNotFoundException(PureArticleServiceImpl.NO_RELATED_ARTICLE_FOR_KEYWORD + query);
        }

        List<PureArticleResponseDto> pureArticles = articlePage
                .getContent()
                .stream()
                .map(ArticleMapper::convertPureArticleToPureArticleResponseDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("totalPureArticleCount", articlePage.getTotalElements());
        response.put("pureArticles", pureArticles);
        return response;
    }

    @Override
    public PureArticleResponseDto getPureArticleById(Long id) {
        PureArticle pureArticle = pureArticleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PureArticleServiceImpl.PURE_ARTICLE_COULD_NOT_BE_FOUND_FOR_ID + id));
        return ArticleMapper.convertPureArticleToPureArticleResponseDto(pureArticle);
    }

    @Override
    public void savePureArticles(Set<PureArticle> pureArticle) {
        pureArticleRepository.saveAll(pureArticle);
    }
}
