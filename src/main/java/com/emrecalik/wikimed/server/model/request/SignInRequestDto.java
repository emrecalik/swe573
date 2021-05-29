package com.emrecalik.wikimed.server.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SignInRequestDto {
    private String userName;
    private String password;
}
