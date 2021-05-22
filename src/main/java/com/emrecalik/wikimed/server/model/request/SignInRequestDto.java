package com.emrecalik.wikimed.server.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInRequestDto {
    private String userName;
    private String password;
}
