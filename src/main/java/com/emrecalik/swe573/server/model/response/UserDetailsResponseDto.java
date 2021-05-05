package com.emrecalik.swe573.server.model.response;

import lombok.*;

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
}
