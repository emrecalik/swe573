package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.Activity;
import com.emrecalik.swe573.server.model.response.activity.ActivityResponseDto;

import java.util.List;

public interface ActivityService {
    void saveActivity(Activity activity);

    List<ActivityResponseDto> getActivityStream(Long userId);

    void deleteActivityByObjectId(Long objectId);
}
