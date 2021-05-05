package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.model.response.ApiResponseDto;
import com.emrecalik.swe573.server.model.request.ArticlePostRequestDto;
import com.emrecalik.swe573.server.model.response.ArticleResponseDto;
import com.emrecalik.swe573.server.domain.Article;

import java.util.Map;

public interface ArticleService {
    ApiResponseDto saveArticle(ArticlePostRequestDto articlePostRequestDto);
    Map<String, Object> getArticlesByUserId(Long userId, int pageNum);
    ApiResponseDto deleteArticleById(Long id);
    ArticleResponseDto getUserArticleById(Long id, Long userId);
    Map<String, Object> getPaginatedArticles(int pageNum, Long userId);
    Article getArticleProxy(Long articleId);
    ApiResponseDto deleteArticleTag(Long articleId, String wikiItemEntityId);
}
