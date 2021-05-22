package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.Activity;
import com.emrecalik.wikimed.server.domain.Article;
import com.emrecalik.wikimed.server.domain.Rate;
import com.emrecalik.wikimed.server.domain.User;
import com.emrecalik.wikimed.server.model.response.ApiResponseDto;
import com.emrecalik.wikimed.server.repository.RateRepository;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {

    private static final String SUCCESS = "Success";

    private static final String VOTE_IS_SUCCESSFULLY_SAVED = "Vote is successfully saved.";

    private final RateRepository rateRepository;

    private final UserService userService;

    private final ArticleService articleService;

    private final ActivityService activityService;

    public RateServiceImpl(RateRepository rateRepository, UserService userService,
                           ArticleService articleService, ActivityService activityService) {
        this.rateRepository = rateRepository;
        this.userService = userService;
        this.articleService = articleService;
        this.activityService = activityService;
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
            Article articleProxy = articleService.getArticleProxyById(articleId);
            Rate rateToSave = Rate.builder()
                    .user(userProxy)
                    .article(articleProxy)
                    .rateValue(rateValue)
                    .build();
            rateRepository.save(rateToSave);
        }

        Activity activity = Activity.builder()
                .summary(" rated ")
                .activityType(Activity.ActivityType.RATE)
                .objectType(Activity.ObjectType.ARTICLE)
                .actorId(userId)
                .objectId(articleId)
                .build();
        activityService.saveActivity(activity);

        return ApiResponseDto.builder()
                .header(RateServiceImpl.SUCCESS)
                .message(RateServiceImpl.VOTE_IS_SUCCESSFULLY_SAVED)
                .build();
    }
}
