package com.emrecalik.wikimed.server.model.request;

import com.emrecalik.wikimed.server.model.response.PureArticleResponseDto;
import com.emrecalik.wikimed.server.model.response.WikiApiResponseDto;
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
    private Set<PureArticleResponseDto> pureArticles;

    @NotNull(message = "No wiki item is selected!")
    private Set<WikiApiResponseDto> wikiItems;
}
