package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.model.response.ApiResponseDto;
import com.emrecalik.swe573.server.model.response.RefreshedAccessTokenResponseDto;
import com.emrecalik.swe573.server.model.request.RefreshTokenRequestDto;
import com.emrecalik.swe573.server.model.request.SignInRequestDto;
import com.emrecalik.swe573.server.model.response.SignInResponseDto;
import com.emrecalik.swe573.server.model.request.SignUpRequestDto;

public interface AuthService {
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);
    ApiResponseDto signUp(SignUpRequestDto signUpRequestDto);
    RefreshedAccessTokenResponseDto refreshAccessToken(RefreshTokenRequestDto refreshTokenRequestDto);
    void signOut(RefreshTokenRequestDto refreshTokenRequestDto);
}
