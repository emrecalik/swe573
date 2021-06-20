package com.emrecalik.wikimed.server.controller.entrez;

import com.emrecalik.wikimed.server.domain.purearticle.PureArticle;
import com.emrecalik.wikimed.server.service.EntrezApiService;
import com.emrecalik.wikimed.server.service.PureArticleService;
import com.google.common.collect.Lists;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(EntrezApiDbController.BASE_URL)
public class EntrezApiDbController {

    public static final String BASE_URL = "/api/entrez/articles";

    private final EntrezApiService entrezApiService;

    private final PureArticleService pureArticleService;

    public EntrezApiDbController(EntrezApiService entrezApiService,
                                 PureArticleService pureArticleService) {
        this.entrezApiService = entrezApiService;
        this.pureArticleService = pureArticleService;
    }

    @PostMapping("/db/populate")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void getArticleIdList() throws InterruptedException {
        String QUERY = "influenza";
        List<String> articleIdList = entrezApiService.getArticleIdList(QUERY);
        List<List<String>> idSubLists = Lists.partition(articleIdList, 80);
        for (List<String> idSubList : idSubLists) {
            savePureArticles(idSubList);
            Thread.sleep(1000);
        }
    }

    private void savePureArticles(List<String> idSubList) {
        Set<PureArticle> pureArticles = entrezApiService.getArticlesByIds(String.join(",", idSubList))
                .stream()
                .filter(pureArticle ->
                        pureArticle.getArticleAbstract() != null
                                && !pureArticle.getArticleAbstract().isEmpty()
                                && pureArticle.getTitle() != null
                                && !pureArticle.getTitle().isEmpty())
                .collect(Collectors.toSet());
        pureArticleService.savePureArticles(pureArticles);
    }
}
