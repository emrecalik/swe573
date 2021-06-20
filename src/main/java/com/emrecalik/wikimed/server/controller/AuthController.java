package com.emrecalik.wikimed.server.controller;

import com.emrecalik.wikimed.server.model.request.RefreshTokenRequestDto;
import com.emrecalik.wikimed.server.model.request.SignInRequestDto;
import com.emrecalik.wikimed.server.model.request.SignUpRequestDto;
import com.emrecalik.wikimed.server.model.response.ApiResponseDto;
import com.emrecalik.wikimed.server.model.response.RefreshedAccessTokenResponseDto;
import com.emrecalik.wikimed.server.model.response.SignInResponseDto;
import com.emrecalik.wikimed.server.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthController.BASE_URL)
public class AuthController {

    public static final String BASE_URL = "/api/auth";

    public static final String SIGN_UP_URL = "/signup";

    public static final String SIGN_IN_URL = "/signin";

    public static final String TOKEN_REFRESH_URL = "/token/refresh";

    public static final String SIGN_OUT_URL = "/signout";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(SIGN_UP_URL)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ApiResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));
    }

    @PostMapping(SIGN_IN_URL)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok(authService.signIn(signInRequestDto));
    }

    @PostMapping(TOKEN_REFRESH_URL)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RefreshedAccessTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshTokenRequestDto));
    }

    @DeleteMapping(SIGN_OUT_URL)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void signOut(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        authService.signOut(refreshTokenRequestDto);
    }
}
