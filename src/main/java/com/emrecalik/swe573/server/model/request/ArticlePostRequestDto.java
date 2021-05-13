package com.emrecalik.swe573.server.model.request;

import com.emrecalik.swe573.server.model.response.EntrezApiResponseDto;
import com.emrecalik.swe573.server.model.response.WikiApiResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ArticlePostRequestDto {

    private Long userId;

    @NotNull(message = "No article is selected!")
    private Set<EntrezApiResponseDto> articles;

    @NotNull(message = "No wiki item is selected!")
    private Set<WikiApiResponseDto> wikiItems;
}
