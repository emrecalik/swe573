package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.model.response.EntrezApiResponseDto;
import com.emrecalik.swe573.server.service.api.entrez.EntrezApi;
import com.emrecalik.swe573.server.service.api.entrez.PubmedArticle;
import com.emrecalik.swe573.server.service.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class EntrezApiServiceImpl implements EntrezApiService {

    private EntrezApi entrezApi;

    public EntrezApiServiceImpl(EntrezApi entrezApi) {
        this.entrezApi = entrezApi;
    }

    @Override
    public Set<EntrezApiResponseDto> getArticles(String query) {
        Set<PubmedArticle> pubmedArticleSet = entrezApi.getPubmedArticleSet(query);
        Set<EntrezApiResponseDto> entrezApiResponseDtoSet = new HashSet<>();
        for (PubmedArticle pubmedArticle : pubmedArticleSet) {
            entrezApiResponseDtoSet.add(ArticleMapper.convertPubmedArticleToPubmedArticleDto(pubmedArticle));
        }
        return entrezApiResponseDtoSet;
    }

    @Override
    public EntrezApiResponseDto getArticleById(String id) {
        PubmedArticle pubmedArticle = entrezApi.getPubmedArticleById(id);
        return ArticleMapper.convertPubmedArticleToPubmedArticleDto(pubmedArticle);
    }
}
