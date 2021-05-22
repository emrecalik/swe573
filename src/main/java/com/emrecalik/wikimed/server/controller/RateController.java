package com.emrecalik.wikimed.server.controller;

import com.emrecalik.wikimed.server.model.response.ApiResponseDto;
import com.emrecalik.wikimed.server.service.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(RateController.BASE_URL)
public class RateController {
    public static final String BASE_URL = "/api/article";

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping(value = "/{id}/rate", params = {"userId", "rateValue"})
    public ResponseEntity<ApiResponseDto> rateArticle(@PathVariable Long id,
                                                      @RequestParam Long userId,
                                                      @RequestParam int rateValue) {
        return ResponseEntity.ok(rateService.saveOrUpdateRate(id, userId, rateValue));
    }
}
