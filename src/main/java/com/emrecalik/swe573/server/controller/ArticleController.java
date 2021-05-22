package com.emrecalik.swe573.server.controller;

import com.emrecalik.swe573.server.model.request.ArticlePostRequestDto;
import com.emrecalik.swe573.server.model.response.ApiResponseDto;
import com.emrecalik.swe573.server.model.response.ArticleResponseDto;
import com.emrecalik.swe573.server.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(ArticleController.BASE_URL)
public class ArticleController {

    public static final String BASE_URL = "/api/article";

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> saveArticle(@Valid @RequestBody ArticlePostRequestDto
                                                              articlePostRequestDto) {
        return ResponseEntity.ok(articleService.saveArticle(articlePostRequestDto));
    }

    @GetMapping(value = "/mylist", params = {"userId", "pageNum"})
    public ResponseEntity<Map<String, Object>> getArticlesByUserId(@RequestParam Long userId,
                                                                   int pageNum) {
        return ResponseEntity.ok(articleService.getArticlesByUserId(userId, pageNum));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.deleteArticleById(id));
    }

    @GetMapping(value = "/{id}", params = {"userId"})
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable Long id,
                                                             @RequestParam Long userId) {
        return ResponseEntity.ok(articleService.getUserArticleById(id, userId));
    }

    @GetMapping(value = "/all", params = {"pageNum", "userId"})
    public ResponseEntity<Map<String, Object>> getAllArticles(@RequestParam int pageNum,
                                                              @RequestParam Long userId) {
        return ResponseEntity.ok(articleService.getPaginatedArticles(pageNum, userId));
    }

    @DeleteMapping("/{articleId}/delete/{wikiItemEntityId}")
    public ResponseEntity<ApiResponseDto> deleteArticleTag(@PathVariable Long articleId,
                                                           @PathVariable String wikiItemEntityId) {
        return ResponseEntity.ok(articleService.deleteArticleTag(articleId, wikiItemEntityId));
    }
}

