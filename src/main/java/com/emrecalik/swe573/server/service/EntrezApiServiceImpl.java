package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.purearticle.PureArticle;
import com.emrecalik.swe573.server.model.response.entrez.EntrezApiResponseDto;
import com.emrecalik.swe573.server.service.api.entrez.EntrezApi;
import com.emrecalik.swe573.server.service.api.entrez.PubmedArticle;
import com.emrecalik.swe573.server.service.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class EntrezApiServiceImpl implements EntrezApiService {

    private final EntrezApi entrezApi;

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

    @Override
    public List<String> getArticleIdList(String query) {
        return entrezApi.getArticleIds(query);
    }

    @Override
    public Set<PureArticle> getArticlesByIds(String idQuery) {
        Set<PubmedArticle> pubmedArticles = entrezApi.getPubmedArticlesByIds(idQuery);
        Set<PureArticle> pureArticles = new HashSet<>();
        pubmedArticles.forEach(pubmedArticle -> pureArticles.add(ArticleMapper.convertPubmedArticleToPureArticle(pubmedArticle)));
        return pureArticles;
    }
}
