package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.Article;
import com.emrecalik.wikimed.server.model.request.ArticlePostRequestDto;
import com.emrecalik.wikimed.server.model.response.ApiResponseDto;
import com.emrecalik.wikimed.server.model.response.ArticleResponseDto;

import java.util.Map;

public interface ArticleService {
    ApiResponseDto saveArticle(ArticlePostRequestDto articlePostRequestDto);

    Map<String, Object> getArticlesByUserId(Long userId, int pageNum);

    ApiResponseDto deleteArticleById(Long id);

    ArticleResponseDto getUserArticleById(Long id, Long userId);

    Map<String, Object> getPaginatedArticles(int pageNum, Long userId);

    Article getArticleProxyById(Long articleId);

    Article getArticleById(Long articleId);

    ApiResponseDto deleteArticleTag(Long articleId, String wikiItemEntityId);

    boolean existsByEntityIdAndUserId(Long entityId, Long userId);

    Article getArticleByEntityIdAndUserId(Long entityId, Long userId);
}
