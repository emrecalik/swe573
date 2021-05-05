package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.model.response.ApiResponseDto;

public interface RateService {
    ApiResponseDto saveOrUpdateRate(Long articleId, Long userId, int rateValue);
}
