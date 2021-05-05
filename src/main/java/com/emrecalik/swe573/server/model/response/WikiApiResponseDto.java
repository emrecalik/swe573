package com.emrecalik.swe573.server.model.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WikiApiResponseDto {
    private String entityId;
    private String conceptUri;
    private String label;
    private String description;
    private List<String> aliases;
}
