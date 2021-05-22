package com.emrecalik.wikimed.server.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenRequestDto {
    private String refreshToken;
}
