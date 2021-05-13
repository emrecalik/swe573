package com.emrecalik.swe573.server.model.response;

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
public class FollowingDetailsResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
}
