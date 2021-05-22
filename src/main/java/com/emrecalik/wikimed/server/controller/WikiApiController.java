package com.emrecalik.wikimed.server.controller;

import com.emrecalik.wikimed.server.model.response.WikiApiResponseDto;
import com.emrecalik.wikimed.server.service.WikiApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(WikiApiController.BASE_URL)
public class WikiApiController {

    public static final String BASE_URL = "/api/wiki";

    private final WikiApiService wikiApiService;

    public WikiApiController(WikiApiService wikiApiService) {
        this.wikiApiService = wikiApiService;
    }

    @GetMapping(value = "", params = {"query"})
    public ResponseEntity<List<WikiApiResponseDto>> getWikiItems(@RequestParam String query) {
        return ResponseEntity.ok(wikiApiService.getWikiItems(query));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<WikiApiResponseDto> getWikiItemById(@PathVariable String id) {
        return ResponseEntity.ok(wikiApiService.getWikiItemById(id));
    }
}
