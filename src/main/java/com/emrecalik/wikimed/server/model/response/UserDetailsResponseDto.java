package com.emrecalik.wikimed.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Set<ArticleResponseDto> ratedArticles;
    private Set<FollowingDetailsResponseDto> followees;
    private Set<FollowingDetailsResponseDto> followers;
    private boolean isFollowedByRequester;
}
