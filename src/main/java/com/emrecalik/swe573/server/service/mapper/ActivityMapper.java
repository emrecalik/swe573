package com.emrecalik.swe573.server.service.mapper;

import com.emrecalik.swe573.server.domain.Activity;
import com.emrecalik.swe573.server.domain.Article;
import com.emrecalik.swe573.server.domain.User;
import com.emrecalik.swe573.server.model.response.activity.ActivityActorResponseDto;
import com.emrecalik.swe573.server.model.response.activity.ActivityObjectResponseDto;
import com.emrecalik.swe573.server.model.response.activity.ActivityResponseDto;
import org.ocpsoft.prettytime.PrettyTime;

public class ActivityMapper {

    private final static PrettyTime prettyTime = new PrettyTime();

    private ActivityMapper() {
    }


    public static ActivityObjectResponseDto covertActivityArticleObjectToActivityObjectResponseDto(Article articleObject) {
        return ActivityObjectResponseDto.builder()
                .id(articleObject.getId())
                .name(articleObject.getTitle())
                .type(Activity.ObjectType.ARTICLE)
                .build();
    }

    public static ActivityObjectResponseDto convertActivityUserObjectToActivityObjectResponseDto(User userObject) {
        return ActivityObjectResponseDto.builder()
                .id(userObject.getId())
                .name(userObject.getFirstName() + " " + userObject.getLastName())
                .type(Activity.ObjectType.USER)
                .build();
    }

    public static ActivityResponseDto convertActivityToActivityResponseDto(Activity activity,
                                                                           User actor,
                                                                           ActivityObjectResponseDto objectResponseDto) {
        ActivityActorResponseDto actorResponseDto = ActivityActorResponseDto.builder()
                .id(actor.getId())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .build();
        String timeAgo = prettyTime.format(activity.getPublished());
        return ActivityResponseDto.builder()
                .summary(activity.getSummary())
                .type(activity.getActivityType())
                .actor(actorResponseDto)
                .object(objectResponseDto)
                .published(activity.getPublished())
                .timeAgo(timeAgo)
                .build();
    }
}
