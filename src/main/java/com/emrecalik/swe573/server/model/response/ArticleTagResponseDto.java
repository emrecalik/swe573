package com.emrecalik.swe573.server.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTagResponseDto {
    private String tagName;
    private String entityId;
    private String conceptUri;
}
