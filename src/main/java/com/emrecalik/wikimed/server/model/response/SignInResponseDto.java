package com.emrecalik.wikimed.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
