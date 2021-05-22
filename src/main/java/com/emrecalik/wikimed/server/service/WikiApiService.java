package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.model.response.WikiApiResponseDto;

import java.util.List;

public interface WikiApiService {
    List<WikiApiResponseDto> getWikiItems(String query);
    WikiApiResponseDto getWikiItemById(String id);
}
