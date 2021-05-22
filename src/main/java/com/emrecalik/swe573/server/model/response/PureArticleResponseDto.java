package com.emrecalik.swe573.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PureArticleResponseDto {
    private Long id;
    private Long entityId;
    private String title;
    private String articleAbstract;
    private List<PureArticleAuthorResponseDto> authors;
    private List<String> keywords;
}
