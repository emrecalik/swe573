package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.model.request.RefreshTokenRequestDto;
import com.emrecalik.wikimed.server.model.request.SignInRequestDto;
import com.emrecalik.wikimed.server.model.request.SignUpRequestDto;
import com.emrecalik.wikimed.server.model.response.ApiResponseDto;
import com.emrecalik.wikimed.server.model.response.RefreshedAccessTokenResponseDto;
import com.emrecalik.wikimed.server.model.response.SignInResponseDto;

public interface AuthService {
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);
    ApiResponseDto signUp(SignUpRequestDto signUpRequestDto);
    RefreshedAccessTokenResponseDto refreshAccessToken(RefreshTokenRequestDto refreshTokenRequestDto);
    void signOut(RefreshTokenRequestDto refreshTokenRequestDto);
}
