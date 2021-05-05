package com.emrecalik.swe573.server.controller;

import com.emrecalik.swe573.server.service.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(RateController.BASE_URL)
public class RateController {
    public static final String BASE_URL = "/api/article";

    private RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping(value = "/{id}/rate", params = { "userId", "rateValue" })
    public ResponseEntity<?> rateArticle(@PathVariable Long id,
                                         @RequestParam Long userId,
                                         @RequestParam int rateValue) {
        return ResponseEntity.ok(rateService.saveOrUpdateRate(id, userId, rateValue));
    }
}
