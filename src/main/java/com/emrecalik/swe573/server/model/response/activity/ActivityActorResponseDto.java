package com.emrecalik.swe573.server.model.response.activity;

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
public class ActivityActorResponseDto {

    private Long id;

    private String firstName;

    private String lastName;
}
