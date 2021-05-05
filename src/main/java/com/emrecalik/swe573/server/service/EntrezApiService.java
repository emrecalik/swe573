package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.model.response.EntrezApiResponseDto;

import java.util.Set;

public interface EntrezApiService {
    Set<EntrezApiResponseDto> getArticles(String query);
    EntrezApiResponseDto getArticleById(String id);
}
