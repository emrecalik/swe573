package com.emrecalik.swe573.server.controller;

import com.emrecalik.swe573.server.model.request.RefreshTokenRequestDto;
import com.emrecalik.swe573.server.model.request.SignInRequestDto;
import com.emrecalik.swe573.server.model.request.SignUpRequestDto;
import com.emrecalik.swe573.server.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));
    }

    @PostMapping(SIGN_IN_URL)
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok(authService.signIn(signInRequestDto));
    }

    @PostMapping(TOKEN_REFRESH_URL)
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshTokenRequestDto));
    }

    @DeleteMapping(SIGN_OUT_URL)
    public void signOut(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        authService.signOut(refreshTokenRequestDto);
    }
}
