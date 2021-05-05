package com.emrecalik.swe573.server.controller;

import com.emrecalik.swe573.server.service.WikiApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WikiApiController.BASE_URL)
public class WikiApiController {
    public static final String BASE_URL = "/api/wiki";

    private WikiApiService wikiApiService;

    public WikiApiController(WikiApiService wikiApiService) {
        this.wikiApiService = wikiApiService;
    }

    @GetMapping(value = "", params = { "query" })
    public ResponseEntity<?> getWikiItems(@RequestParam String query) {
        return ResponseEntity.ok(wikiApiService.getWikiItems(query));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getWikiItemById(@PathVariable String id) {
        return ResponseEntity.ok(wikiApiService.getWikiItemById(id));
    }
}
