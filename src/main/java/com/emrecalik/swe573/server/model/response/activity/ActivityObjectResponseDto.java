package com.emrecalik.swe573.server.model.response.activity;

import com.emrecalik.swe573.server.domain.Activity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityObjectResponseDto {

    private Long id;

    private Activity.ObjectType type;

    private String name;
}
