package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.Activity;
import com.emrecalik.wikimed.server.model.response.activity.ActivityResponseDto;

import java.util.List;

public interface ActivityService {
    void saveActivity(Activity activity);

    List<ActivityResponseDto> getActivityStream(Long userId);

    void deleteActivityByObjectId(Long objectId);
}
