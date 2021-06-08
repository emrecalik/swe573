package com.emrecalik.wikimed.server.service.api.entrez;

import com.emrecalik.wikimed.server.exception.ExternalApiException;
import com.emrecalik.wikimed.server.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
public class EntrezApi {

    private static final String NOT_FOUND_ARTICLE_FOR_KEYWORD = "Not found article for keyword = ";

    private static final String ENTREZ_API_DOES_NOT_RESPOND = "Entrez API does not respond!";

    @Value("${entrez.api.db}")
    private String apiDb;

    @Value("${entrez.api.esearch.path}")
    private String apiEsearchPath;

    @Value("${entrez.api.efetch.path}")
    private String apiEfetchPath;

    @Value("${entrez.api.version}")
    private String apiVersion;

    @Value("${entrez.api.key}")
    private String apiKey;

    @Value("${entrez.api.retmax}")
    private int apiRetmax;

    @Value("${entrez.api.rettype}")
    private String apiRettype;

    private WebClient webClient;

    public EntrezApi(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<String> getArticleIds(String query) {
        List<String> idList = Objects.requireNonNull(webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiEsearchPath)
                        .queryParam("db", apiDb)
                        .queryParam("term", query)
                        .queryParam("api_key", apiKey)
                        .queryParam("retMax", apiRetmax)
                        .build())
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(ArticleIdList.class)
                .block()).getIdList();

        if (idList == null || idList.isEmpty()) {
            throw new ResourceNotFoundException(EntrezApi.NOT_FOUND_ARTICLE_FOR_KEYWORD + query);
        }
        return idList;
    }

    public Set<PubmedArticle> getPubmedArticlesByIds(String idQuery) {
        URI uri = UriComponentsBuilder
                .fromPath(apiEfetchPath)
                .queryParam("db", apiDb)
                .queryParam("id", idQuery)
                .queryParam("rettype", apiRettype)
                .queryParam("version", apiVersion)
                .queryParam("api_key", apiKey)
                .build().toUri();

        log.info("PubMed URI => " + uri.toString());
        try {
            return Objects.requireNonNull(webClient.get()
                    .uri(uri.toString())
                    .accept(MediaType.APPLICATION_XML)
                    .retrieve()
                    .bodyToMono(PubmedArticleSet.class)
                    .block()).getPubmedArticleSet();
        } catch (Exception ignored) {
            return getPubmedArticlesByIds(idQuery);
        }
    }

    public Set<PubmedArticle> getPubmedArticleSet(String query) {
        String idQuery = String.join(",", getArticleIds(query));
        return getPubmedArticlesByIds(idQuery);
    }

    public PubmedArticle getPubmedArticleById(String id) {
        Set<PubmedArticle> pubmedArticleSet = getPubmedArticleSet(id);
        if (pubmedArticleSet == null) {
            throw new ExternalApiException(EntrezApi.ENTREZ_API_DOES_NOT_RESPOND);
        }
        return pubmedArticleSet.iterator().next();
    }

}
