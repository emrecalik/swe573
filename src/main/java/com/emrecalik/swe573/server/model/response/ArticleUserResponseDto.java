package com.emrecalik.swe573.server.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUserResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
}
