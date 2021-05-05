package com.emrecalik.swe573.server.service.api.entrez;

import com.emrecalik.swe573.server.exception.ExternalApiException;
import com.emrecalik.swe573.server.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
public class EntrezApi {
    private String API_DB = "pubmed";
    private String API_ESEARCH_PATH = "/esearch.fcgi";
    private String API_EFETCH_PATH = "/efetch.fcgi";
    private String API_VERSION = "2.0";
    private String API_KEY = "4b48bebc2298f58802f12948c049d873d908";

    private WebClient webClient;

    public EntrezApi(WebClient webClient) {
        this.webClient = webClient;
    }

    private String getArticleIds(String query) {
        Set<String> idList = Objects.requireNonNull(webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(API_ESEARCH_PATH)
                        .queryParam("db", API_DB)
                        .queryParam("term", query)
                        .queryParam("api_key", API_KEY)
                        .build())
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(ArticleIdList.class)
                .block()).getIdList();

        if (idList == null || idList.size() == 0) {
            throw new ResourceNotFoundException("Not found article for keyword = " + query);
        }

        return String.join(",", idList);
    }

    public Set<PubmedArticle> getPubmedArticleSet(String query) {
        String idQuery = getArticleIds(query);
        URI uri = UriComponentsBuilder
                .fromPath(API_EFETCH_PATH)
                .queryParam("db", API_DB)
                .queryParam("id", idQuery)
                .queryParam("rettype", "xml")
                .queryParam("version", API_VERSION)
                .queryParam("api_key", API_KEY)
                .build().toUri();

        log.info("PubMed URI => " + uri.toString());
        try {
            return Objects.requireNonNull(webClient.get()
                    .uri(uri.toString())
                    .accept(MediaType.APPLICATION_XML)
                    .retrieve()
                    .bodyToMono(PubmedArticleSet.class)
                    .block()).getPubmedArticleSet();
        } catch (Exception ex) {
            throw new ExternalApiException("Entrez API does not respond!");
        }
    }

    public PubmedArticle getPubmedArticleById(String id) {
        Set<PubmedArticle> pubmedArticleSet = getPubmedArticleSet(id);
        if (pubmedArticleSet == null) {
            throw new ExternalApiException("Entrez API does not respond!");
        }
        return pubmedArticleSet.iterator().next();
    }

}
