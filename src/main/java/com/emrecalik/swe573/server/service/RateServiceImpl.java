package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.Article;
import com.emrecalik.swe573.server.domain.Rate;
import com.emrecalik.swe573.server.domain.User;
import com.emrecalik.swe573.server.model.response.ApiResponseDto;
import com.emrecalik.swe573.server.repository.RateRepository;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {

    private static final String SUCCESS = "Success";

    private static final String VOTE_IS_SUCCESSFULLY_SAVED = "Vote is successfully saved.";

    private final RateRepository rateRepository;

    private final UserService userService;

    private final ArticleService articleService;

    public RateServiceImpl(RateRepository rateRepository,
                           UserService userService,
                           ArticleService articleService) {
        this.rateRepository = rateRepository;
        this.userService = userService;
        this.articleService = articleService;
    }

    @Override
    public ApiResponseDto saveOrUpdateRate(Long articleId, Long userId, int rateValue) {
        Rate rateToUpdate = rateRepository.findByUserIdAndArticleId(userId, articleId)
                .orElse(null);
        if (rateToUpdate != null) {
            rateToUpdate.setRateValue(rateValue);
            rateRepository.save(rateToUpdate);
        } else {
            User userProxy = userService.getUserProxyById(userId);
            Article articleProxy = articleService.getArticleProxy(articleId);
            Rate rateToSave = Rate.builder()
                    .user(userProxy)
                    .article(articleProxy)
                    .rateValue(rateValue)
                    .build();
            rateRepository.save(rateToSave);
        }
        return ApiResponseDto.builder()
                .header(RateServiceImpl.SUCCESS)
                .message(RateServiceImpl.VOTE_IS_SUCCESSFULLY_SAVED)
                .build();
    }
}
