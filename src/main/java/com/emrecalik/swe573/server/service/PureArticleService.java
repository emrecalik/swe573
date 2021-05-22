package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.purearticle.PureArticle;
import com.emrecalik.swe573.server.model.response.PureArticleResponseDto;

import java.util.Map;
import java.util.Set;

public interface PureArticleService {
    Map<String, Object> queryPureArticles(String query, int pageNum);

    PureArticleResponseDto getPureArticleById(Long id);

    void savePureArticles(Set<PureArticle> pureArticle);
}
