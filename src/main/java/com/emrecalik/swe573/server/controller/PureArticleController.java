package com.emrecalik.swe573.server.controller;

import com.emrecalik.swe573.server.service.PureArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(PureArticleController.BASE_URL)
public class PureArticleController {

    public static final String BASE_URL = "/api/pureArticles";

    private final PureArticleService pureArticleService;

    public PureArticleController(PureArticleService pureArticleService) {
        this.pureArticleService = pureArticleService;
    }

    @GetMapping(value = "", params = {"query", "pageNum"})
    public ResponseEntity<Map<String, Object>> queryPureArticles(@RequestParam String query,
                                                                 @RequestParam int pageNum) {
        return ResponseEntity.ok(pureArticleService.queryPureArticles(query, pageNum));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPureArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(pureArticleService.getPureArticleById(id));
    }
}
