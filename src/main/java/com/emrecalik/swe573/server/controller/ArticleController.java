package com.emrecalik.swe573.server.controller;

import com.emrecalik.swe573.server.model.request.ArticlePostRequestDto;
import com.emrecalik.swe573.server.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ArticleController.BASE_URL)
public class ArticleController {

    public static final String BASE_URL = "/api/article";

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    public ResponseEntity<?> saveArticle(@RequestBody ArticlePostRequestDto
                                                     articlePostRequestDto) {
        return ResponseEntity.ok(articleService.saveArticle(articlePostRequestDto));
    }

    @GetMapping(value = "/mylist", params = { "userId", "pageNum" })
    public ResponseEntity<?> getArticlesByUserId(@RequestParam Long userId, int pageNum) {
        return ResponseEntity.ok(articleService.getArticlesByUserId(userId, pageNum));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.deleteArticleById(id));
    }

    @GetMapping(value = "/{id}", params = { "userId"})
    public ResponseEntity<?> getArticleById(@PathVariable Long id,
                                            @RequestParam Long userId) {
        return ResponseEntity.ok(articleService.getUserArticleById(id, userId));
    }

    @GetMapping(value = "/all", params = { "pageNum", "userId" })
    public ResponseEntity<?> getAllArticles(@RequestParam int pageNum,
                                            @RequestParam Long userId) {
        return ResponseEntity.ok(articleService.getPaginatedArticles(pageNum, userId));
    }

    @DeleteMapping("/{articleId}/delete/{wikiItemEntityId}")
    public ResponseEntity<?> deleteArticleTag(@PathVariable Long articleId,
                                              @PathVariable String wikiItemEntityId) {
        return ResponseEntity.ok(articleService.deleteArticleTag(articleId, wikiItemEntityId));
    }
}

