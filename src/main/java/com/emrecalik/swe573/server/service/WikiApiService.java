package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.model.response.WikiApiResponseDto;

import java.util.List;

public interface WikiApiService {
    List<WikiApiResponseDto> getWikiItems(String query);
    WikiApiResponseDto getWikiItemById(String id);
}
