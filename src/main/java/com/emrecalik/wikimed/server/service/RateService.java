package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.model.response.ApiResponseDto;

public interface RateService {
    ApiResponseDto saveOrUpdateRate(Long articleId, Long userId, int rateValue);
}
