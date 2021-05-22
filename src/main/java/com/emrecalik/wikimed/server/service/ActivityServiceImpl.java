package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.Activity;
import com.emrecalik.wikimed.server.domain.Article;
import com.emrecalik.wikimed.server.domain.User;
import com.emrecalik.wikimed.server.model.response.activity.ActivityObjectResponseDto;
import com.emrecalik.wikimed.server.model.response.activity.ActivityResponseDto;
import com.emrecalik.wikimed.server.repository.ActivityRepository;
import com.emrecalik.wikimed.server.service.mapper.ActivityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final UserService userService;

    private final ArticleService articleService;

    public ActivityServiceImpl(ActivityRepository activityRepository,
                               @Lazy UserService userService,
                               @Lazy ArticleService articleService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
        this.articleService = articleService;
    }

    @Override
    public void saveActivity(Activity activity) {
        activity.setPublished(Instant.now());
        activityRepository.save(activity);
        log.info("Activity has been saved.");
    }


    @Override
    public List<ActivityResponseDto> getActivityStream(Long userId) {
        User user = userService.getUserById(userId);
        List<Long> actorIdList = user.getFollowees().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        List<ActivityResponseDto> activityResponseDtoList = new ArrayList<>();
        List<Activity> activityList = activityRepository.findAllByActorIdInOrderByPublishedDesc(actorIdList);
        for (Activity activity : activityList) {
            User actor = userService.getUserById(activity.getActorId());
            if (activity.getObjectType() == null) {
                activityResponseDtoList.add(ActivityMapper.convertActivityToActivityResponseDto(activity, actor, null));
            } else if (activity.getObjectType() == Activity.ObjectType.ARTICLE) {
                Article articleObject = articleService.getArticleById(activity.getObjectId());
                ActivityObjectResponseDto objectResponseDto = ActivityMapper.covertActivityArticleObjectToActivityObjectResponseDto(articleObject);
                activityResponseDtoList.add(ActivityMapper.convertActivityToActivityResponseDto(activity, actor, objectResponseDto));
            } else {
                User userObject = userService.getUserById(activity.getObjectId());
                ActivityObjectResponseDto objectResponseDto = ActivityMapper.convertActivityUserObjectToActivityObjectResponseDto(userObject);
                activityResponseDtoList.add(ActivityMapper.convertActivityToActivityResponseDto(activity, actor, objectResponseDto));
            }
        }
        return activityResponseDtoList;
    }

    @Override
    public void deleteActivityByObjectId(Long objectId) {
        activityRepository.deleteByObjectId(objectId);
        log.info("Activity is deleted.");
    }
}
