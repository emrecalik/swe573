package com.emrecalik.swe573.server.controller;

import com.emrecalik.swe573.server.service.EntrezApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(EntrezApiController.BASE_URL)
public class EntrezApiController {

    public static final String BASE_URL = "/api/entrez/article";

    private final EntrezApiService entrezApiService;

    public EntrezApiController(EntrezApiService entrezApiService) {
        this.entrezApiService = entrezApiService;
    }

    @GetMapping(value = "", params = { "query" })
    public ResponseEntity<?> getArticles(@RequestParam String query) {
        return ResponseEntity.ok(entrezApiService.getArticles(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable String id) {
        return ResponseEntity.ok(entrezApiService.getArticleById(id));
    }
}
