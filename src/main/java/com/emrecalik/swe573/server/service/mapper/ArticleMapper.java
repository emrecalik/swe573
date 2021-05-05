package com.emrecalik.swe573.server.service.mapper;

import com.emrecalik.swe573.server.model.response.ArticleResponseDto;
import com.emrecalik.swe573.server.model.response.ArticleTagResponseDto;
import com.emrecalik.swe573.server.model.response.ArticleUserResponseDto;
import com.emrecalik.swe573.server.model.response.EntrezApiResponseDto;
import com.emrecalik.swe573.server.model.response.ArticleAuthorResponseDto;
import com.emrecalik.swe573.server.domain.*;
import com.emrecalik.swe573.server.service.api.entrez.PubmedArticle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleMapper {

    public static EntrezApiResponseDto convertPubmedArticleToPubmedArticleDto(PubmedArticle pubmedArticle) {

        String articleAbstract = pubmedArticle.getMedlineCitation().getArticle().getAbstractText() == null ?
                null : String.join(" ", pubmedArticle.getMedlineCitation().getArticle().getAbstractText());

        List<PubmedArticle.MedlineCitation.Article.Author> fetchedAuthorList = pubmedArticle
                .getMedlineCitation().getArticle().getAuthorList();

        List<ArticleAuthorResponseDto> authors = fetchedAuthorList == null ?
                null : fetchedAuthorList.stream().map(author -> ArticleAuthorResponseDto.builder()
                        .lastName(author.getLastName())
                        .foreName(author.getForeName())
                        .build()).collect(Collectors.toList());

        return EntrezApiResponseDto.builder()
                .entityId(Long.parseLong(pubmedArticle.getMedlineCitation().getId()))
                .title(pubmedArticle.getMedlineCitation().getArticle().getArticleTitle())
                .articleAbstract(articleAbstract)
                .authors(authors)
                .keywords(pubmedArticle.getMedlineCitation().getKeywordList())
                .build();
    }

    public static Article convertArticleApiDtoToArticle(EntrezApiResponseDto entrezApiResponseDto) {
        Set<ArticleAuthor> articleAuthors = new HashSet<>();
        for (ArticleAuthorResponseDto articleAuthorResponseDto : entrezApiResponseDto.getAuthors()) {
            articleAuthors.add(ArticleAuthor.builder()
            .foreName(articleAuthorResponseDto.getForeName())
            .lastName(articleAuthorResponseDto.getLastName())
            .build());
        }

        return Article.builder()
                .entityId(entrezApiResponseDto.getEntityId())
                .title(entrezApiResponseDto.getTitle())
                .articleAbstract(entrezApiResponseDto.getArticleAbstract())
                .authors(articleAuthors)
                .keywords(entrezApiResponseDto.getKeywords() == null ? null :
                        new HashSet<>(entrezApiResponseDto.getKeywords()))
                .build();
    }

    public static ArticleResponseDto convertArticleToArticleResponseDto(Article article, Long userId) {

        Set<ArticleTagResponseDto> articleTagResponseDtos = new HashSet<>();
        for (WikiItem wikiItem : article.getWikiItems()) {
            articleTagResponseDtos.add(ArticleTagResponseDto.builder()
                    .tagName(wikiItem.getLabel())
                    .entityId(wikiItem.getEntityId())
                    .conceptUri(wikiItem.getConceptUri())
                    .build());
        }

        Set<ArticleAuthorResponseDto> articleAuthorResponseDtos = new HashSet<>();
        for (ArticleAuthor articleAuthor : article.getAuthors()) {
            articleAuthorResponseDtos.add(ArticleAuthorResponseDto.builder()
                    .foreName(articleAuthor.getForeName())
                    .lastName(articleAuthor.getLastName())
                    .build());
        }

        Set<String> articleKeywords = article.getKeywords();
        User tagger = article.getUser();
        Rate rate = article.getRates().stream()
                .filter(item -> item.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);

        return ArticleResponseDto.builder()
                .id(article.getId())
                .entityId(article.getEntityId())
                .title(article.getTitle())
                .articleAbstract(article.getArticleAbstract())
                .tags(articleTagResponseDtos)
                .authors(articleAuthorResponseDtos)
                .keywords(articleKeywords == null ? null :
                        new HashSet<>(articleKeywords))
                .user(ArticleUserResponseDto.builder()
                        .userId(tagger.getId())
                        .firstName(tagger.getFirstName())
                        .lastName(tagger.getLastName())
                        .build())
                .rate(rate == null ? null : rate.getRateValue())
                .build();
    }
}
