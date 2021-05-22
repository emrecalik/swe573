package com.emrecalik.wikimed.server.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequestDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
