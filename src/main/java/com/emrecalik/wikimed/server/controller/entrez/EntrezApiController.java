package com.emrecalik.wikimed.server.controller.entrez;

import com.emrecalik.wikimed.server.model.response.entrez.EntrezApiResponseDto;
import com.emrecalik.wikimed.server.service.EntrezApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Profile("dev")
@Slf4j
@RestController
@RequestMapping(EntrezApiController.BASE_URL)
public class EntrezApiController {

    public static final String BASE_URL = "/api/entrez/articles";

    private final EntrezApiService entrezApiService;

    public EntrezApiController(EntrezApiService entrezApiService) {
        this.entrezApiService = entrezApiService;
    }

    @GetMapping(value = "", params = {"query"})
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Set<EntrezApiResponseDto>> getArticles(@RequestParam String query) {
        return ResponseEntity.ok(entrezApiService.getArticles(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<EntrezApiResponseDto> getArticleById(@PathVariable String id) {
        return ResponseEntity.ok(entrezApiService.getArticleById(id));
    }
}
