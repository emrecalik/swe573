package com.emrecalik.wikimed.server.controller.entrez;

import com.emrecalik.wikimed.server.model.response.entrez.EntrezApiResponseDto;
import com.emrecalik.wikimed.server.service.EntrezApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping(EntrezApiController.BASE_URL)
public class EntrezApiController {

    public static final String BASE_URL = "/api/entrez/article";

    private final EntrezApiService entrezApiService;

    public EntrezApiController(EntrezApiService entrezApiService) {
        this.entrezApiService = entrezApiService;
    }

    @GetMapping(value = "", params = {"query"})
    public ResponseEntity<Set<EntrezApiResponseDto>> getArticles(@RequestParam String query) {
        return ResponseEntity.ok(entrezApiService.getArticles(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrezApiResponseDto> getArticleById(@PathVariable String id) {
        return ResponseEntity.ok(entrezApiService.getArticleById(id));
    }
}
