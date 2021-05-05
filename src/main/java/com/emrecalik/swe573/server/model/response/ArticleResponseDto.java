package com.emrecalik.swe573.server.model.response;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private Long entityId;
    private String title;
    private String articleAbstract;
    private Set<ArticleAuthorResponseDto> authors;
    private Set<ArticleTagResponseDto> tags;
    private Set<String> keywords;
    private ArticleUserResponseDto user;
    private Integer rate;
}
