package com.emrecalik.swe573.server.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshedAccessTokenResponseDto {
    private String refreshedAccessToken;
}
