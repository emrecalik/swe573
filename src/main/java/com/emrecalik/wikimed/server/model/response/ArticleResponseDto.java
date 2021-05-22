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
