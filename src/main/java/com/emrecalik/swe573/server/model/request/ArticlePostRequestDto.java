package com.emrecalik.swe573.server.model.request;

import com.emrecalik.swe573.server.model.response.EntrezApiResponseDto;
import com.emrecalik.swe573.server.model.response.WikiApiResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ArticlePostRequestDto {
    private Long userId;
    private Set<EntrezApiResponseDto> articles;
    private Set<WikiApiResponseDto> wikiItems;
}
