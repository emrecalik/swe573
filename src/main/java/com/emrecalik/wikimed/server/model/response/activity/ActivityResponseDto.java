package com.emrecalik.wikimed.server.model.response.activity;

import com.emrecalik.wikimed.server.domain.Activity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponseDto {

    @JsonProperty("@content")
    private final String CONTENT = "https://www.w3.org/ns/activitystreams";

    private String summary;

    private Activity.ActivityType type;

    private ActivityActorResponseDto actor;

    private ActivityObjectResponseDto object;

    private Instant published;

    private String timeAgo;

    @JsonProperty("@content")
    public String getCONTENT() {
        return CONTENT;
    }
}
