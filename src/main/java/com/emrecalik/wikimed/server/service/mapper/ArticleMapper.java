package com.emrecalik.wikimed.server.service.mapper;

import com.emrecalik.wikimed.server.domain.Article;
import com.emrecalik.wikimed.server.domain.ArticleAuthor;
import com.emrecalik.wikimed.server.domain.Rate;
import com.emrecalik.wikimed.server.domain.User;
import com.emrecalik.wikimed.server.domain.WikiItem;
import com.emrecalik.wikimed.server.domain.purearticle.PureArticle;
import com.emrecalik.wikimed.server.domain.purearticle.PureArticleAuthor;
import com.emrecalik.wikimed.server.model.response.ArticleAuthorResponseDto;
import com.emrecalik.wikimed.server.model.response.ArticleResponseDto;
import com.emrecalik.wikimed.server.model.response.ArticleTagResponseDto;
import com.emrecalik.wikimed.server.model.response.ArticleUserResponseDto;
import com.emrecalik.wikimed.server.model.response.PureArticleAuthorResponseDto;
import com.emrecalik.wikimed.server.model.response.PureArticleResponseDto;
import com.emrecalik.wikimed.server.model.response.entrez.EntrezApiResponseDto;
import com.emrecalik.wikimed.server.service.api.entrez.PubmedArticle;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleMapper {

    private ArticleMapper() {
    }

    public static EntrezApiResponseDto convertPubmedArticleToPubmedArticleDto(PubmedArticle pubmedArticle) {

//        String articleAbstract = pubmedArticle.getMedlineCitation().getArticle().getAbstractText() == null ?
//                null : String.join(" ", pubmedArticle.getMedlineCitation().getArticle().getAbstractText());

        String articleAbstract = null;

        List<PubmedArticle.MedlineCitation.Article.Author> fetchedAuthorList = pubmedArticle
                .getMedlineCitation().getArticle().getAuthorList();

        List<ArticleAuthorResponseDto> authors = fetchedAuthorList == null ?
                null : fetchedAuthorList.stream().map(author -> ArticleAuthorResponseDto.builder()
                .lastName(author.getLastName())
                .foreName(author.getForeName())
                .build()).collect(Collectors.toList());

        return EntrezApiResponseDto.builder()
                .entityId(Long.parseLong(pubmedArticle.getMedlineCitation().getId()))
//                .title(pubmedArticle.getMedlineCitation().getArticle().getArticleTitle())
                .articleAbstract(articleAbstract)
                .authors(authors)
                .keywords(pubmedArticle.getMedlineCitation().getKeywordList())
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

    public static PureArticleResponseDto convertPureArticleToPureArticleResponseDto(PureArticle pureArticle) {

        List<PureArticleAuthorResponseDto> pureArticleAuthorResponseDtos = new ArrayList<>();
        for (PureArticleAuthor pureArticleAuthor : pureArticle.getAuthors()) {
            pureArticleAuthorResponseDtos.add(PureArticleAuthorResponseDto.builder()
                    .foreName(pureArticleAuthor.getForeName())
                    .lastName(pureArticleAuthor.getLastName())
                    .build());
        }

        Set<String> pureArticleKeywords = pureArticle.getKeywords();

        return PureArticleResponseDto.builder()
                .id(pureArticle.getId())
                .entityId(pureArticle.getEntityId())
                .title(pureArticle.getTitle())
                .articleAbstract(pureArticle.getArticleAbstract())
                .authors(pureArticleAuthorResponseDtos)
                .keywords(pureArticleKeywords == null ? null : new ArrayList<>(pureArticleKeywords))
                .build();
    }

    public static Article convertPureArticleResponseDtoToArticle(PureArticleResponseDto pureArticleResponseDto) {
        Set<ArticleAuthor> articleAuthors = new HashSet<>();
        for (PureArticleAuthorResponseDto pureArticleAuthorResponseDto : pureArticleResponseDto.getAuthors()) {
            articleAuthors.add(ArticleAuthor.builder()
                    .foreName(pureArticleAuthorResponseDto.getForeName())
                    .lastName(pureArticleAuthorResponseDto.getLastName())
                    .build());
        }

        return Article.builder()
                .entityId(pureArticleResponseDto.getEntityId())
                .title(pureArticleResponseDto.getTitle())
                .articleAbstract(pureArticleResponseDto.getArticleAbstract())
                .authors(articleAuthors)
                .keywords(pureArticleResponseDto.getKeywords() == null ? null :
                        new HashSet<>(pureArticleResponseDto.getKeywords()))
                .build();
    }

    public static PureArticle convertPubmedArticleToPureArticle(PubmedArticle pubmedArticle) {
        PubmedArticle.MedlineCitation.Article.Abstract pubmedArticleAbstract
                = pubmedArticle.getMedlineCitation().getArticle().getAbstractText();
        String articleAbstract = null;
        if (pubmedArticleAbstract != null && pubmedArticleAbstract.getMixedContent() != null) {
            List<Object> abstractMixedContentList = pubmedArticleAbstract.getMixedContent();
            articleAbstract = getAbstractText(abstractMixedContentList);
        }

        PubmedArticle.MedlineCitation.Article.ArticleTitle pubmedArticleTitle
                = pubmedArticle.getMedlineCitation().getArticle().getArticleTitle();
        String articleTitle = null;
        if (pubmedArticleTitle != null && pubmedArticleTitle.getMixedContent() != null) {
            List<Object> titleMixedContentList = pubmedArticleTitle.getMixedContent();
            articleTitle = getTitleText(titleMixedContentList);
        }

        List<PubmedArticle.MedlineCitation.Article.Author> fetchedAuthorList = pubmedArticle
                .getMedlineCitation().getArticle().getAuthorList();

        Set<PureArticleAuthor> authors = fetchedAuthorList == null ?
                null : fetchedAuthorList.stream().map(author -> PureArticleAuthor.builder()
                .lastName(author.getLastName())
                .foreName(author.getForeName())
                .build()).collect(Collectors.toSet());

        List<String> keywords = pubmedArticle.getMedlineCitation().getKeywordList();

        return PureArticle.builder()
                .entityId(Long.parseLong(pubmedArticle.getMedlineCitation().getId()))
                .articleAbstract(articleAbstract)
                .title(articleTitle)
                .authors(authors)
                .keywords(keywords == null ? null : new HashSet<>(keywords))
                .build();
    }


    private static String getAbstractText(List<Object> mixedContentList) {
        StringBuilder text = new StringBuilder("");
        for (Object mixedContent : mixedContentList) {
            try {
                ElementNSImpl content = (ElementNSImpl) mixedContent;
                if (content.getLocalName() == null
                        || content.getLocalName().equals("CopyrightInformation")) {
                    break;
                }
                Node node = content.getFirstChild();
                while (node.getNextSibling() != null) {
                    text.append(node.getTextContent());
                    node = node.getNextSibling();
                }
                text.append(node.getTextContent());
            } catch (Exception ignored) {
            }
        }
        return text.toString();
    }

    private static String getTitleText(List<Object> mixedContentList) {
        StringBuilder text = new StringBuilder("");
        for (Object mixedContent : mixedContentList) {
            if (mixedContent instanceof String) {
                text.append(mixedContent);
            } else {
                ElementNSImpl content = (ElementNSImpl) mixedContent;
                if (content.getLocalName() == null) {
                    break;
                }
                Node node = content.getFirstChild();
                while (node.getNextSibling() != null) {
                    text.append(node.getTextContent());
                    node = node.getNextSibling();
                }
                text.append(node.getTextContent());
            }
        }

        return text.toString();
    }
}

