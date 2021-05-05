package com.emrecalik.swe573.server.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String role;
}
