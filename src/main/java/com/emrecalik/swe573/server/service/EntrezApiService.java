package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.purearticle.PureArticle;
import com.emrecalik.swe573.server.model.response.entrez.EntrezApiResponseDto;

import java.util.List;
import java.util.Set;

public interface EntrezApiService {
    Set<EntrezApiResponseDto> getArticles(String query);

    EntrezApiResponseDto getArticleById(String id);

    List<String> getArticleIdList(String query);

    Set<PureArticle> getArticlesByIds(String idQuery);
}
