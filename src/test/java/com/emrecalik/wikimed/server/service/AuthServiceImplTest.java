package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.Activity;
import com.emrecalik.wikimed.server.domain.RefreshToken;
import com.emrecalik.wikimed.server.domain.Role;
import com.emrecalik.wikimed.server.domain.User;
import com.emrecalik.wikimed.server.exception.BadRequestException;
import com.emrecalik.wikimed.server.exception.ResourceNotFoundException;
import com.emrecalik.wikimed.server.model.request.RefreshTokenRequestDto;
import com.emrecalik.wikimed.server.model.request.SignInRequestDto;
import com.emrecalik.wikimed.server.model.request.SignUpRequestDto;
import com.emrecalik.wikimed.server.model.response.ApiResponseDto;
import com.emrecalik.wikimed.server.model.response.RefreshedAccessTokenResponseDto;
import com.emrecalik.wikimed.server.model.response.SignInResponseDto;
import com.emrecalik.wikimed.server.repository.RefreshTokenRepository;
import com.emrecalik.wikimed.server.security.JwtUtility;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtility jwtUtility;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void signIn() {
        // given
        final String USER_NAME = "emreboun";
        final String PASSWORD = "dummy-password";
        final Role.RoleName USER_ROLE = Role.RoleName.ROLE_USER;
        final String ACCESS_TOKEN = "dummy-jwt";

        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .userName(USER_NAME)
                .password(PASSWORD)
                .build();

        User signedInUser = User.builder()
                .userName(USER_NAME)
                .role(Role.builder().roleName(USER_ROLE).build())
                .build();

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtility.generateToken(authentication)).thenReturn(ACCESS_TOKEN);
        when(userService.findByUserName(USER_NAME)).thenReturn(signedInUser);

        // when
        SignInResponseDto signInResponseDto = authService.signIn(signInRequestDto);

        // then
        assertEquals(USER_NAME, signInRequestDto.getUserName());
        String s = USER_ROLE.name();
        assertEquals(s.substring(s.indexOf("_") + 1), signInResponseDto.getRole());
        assertEquals(ACCESS_TOKEN, signInResponseDto.getAccessToken());
        verify(activityService, times(1)).saveActivity(any(Activity.class));
    }

    @Nested
    class SignUp {

        private final String USER_NAME = "emreboun";
        private final String EMAIL = "emre.calik01@gmail.com";

        private SignUpRequestDto signUpRequestDto;

        @Test
        void signUpNoException() {
            // given
            signUpRequestDto = SignUpRequestDto.builder()
                    .userName(USER_NAME)
                    .email(EMAIL)
                    .build();

            when(userService.existByUserName(USER_NAME)).thenReturn(false);
            when(userService.existByEmail(EMAIL)).thenReturn(false);

            // when
            ApiResponseDto apiResponseDto = authService.signUp(signUpRequestDto);

            // then
            verify(userService, times(1)).existByUserName(USER_NAME);
            verify(userService, times(1)).existByEmail(EMAIL);
            verify(roleService, times(1)).getRoleByRoleName(Role.RoleName.ROLE_USER);
            verify(userService, times(1)).save(any(User.class));
            assertEquals(AuthServiceImpl.SUCCESS, apiResponseDto.getHeader());
            assertEquals(AuthServiceImpl.USER_IS_SUCCESSFULLY_REGISTERED, apiResponseDto.getMessage());
        }

        @Test
        void signUpUserNameException() {
            // given
            final String USER_NAME_TAKEN = "emrebounnnn";
            signUpRequestDto = SignUpRequestDto.builder()
                    .userName(USER_NAME_TAKEN)
                    .email(EMAIL)
                    .build();

            when(userService.existByUserName(USER_NAME_TAKEN)).thenReturn(true);

            // then
            assertThrows(BadRequestException.class, () -> authService.signUp(signUpRequestDto));
        }

        @Test
        void signUpEmailException() {
            // given
            final String EMAIL_TAKEN = "emre.calikkkk01@gmail.com";
            signUpRequestDto = SignUpRequestDto.builder()
                    .userName(USER_NAME)
                    .email(EMAIL_TAKEN)
                    .build();

            when(userService.existByUserName(USER_NAME)).thenReturn(false);
            when(userService.existByEmail(EMAIL_TAKEN)).thenReturn(true);

            // when
            assertThrows(BadRequestException.class, () -> authService.signUp(signUpRequestDto));
        }
    }

    @Test
    void refreshAccessToken() {
        // given
        final String REFRESH_TOKEN = "refresh-token";
        final String REFRESH_TOKEN_NOT_EXIST = "refresh-token-not-exist";
        final String USER_NAME = "emreboun";
        final String REFRESHED_ACCESS_TOKEN = "refreshed-access-token";

        RefreshToken refreshToken = RefreshToken.builder()
                .token(REFRESH_TOKEN)
                .user(User.builder()
                        .userName(USER_NAME)
                        .build())
                .build();

        when(refreshTokenRepository.findByToken(REFRESH_TOKEN)).thenReturn(Optional.ofNullable(refreshToken));
        when(jwtUtility.doGenerateToken(USER_NAME)).thenReturn(REFRESHED_ACCESS_TOKEN);

        // when
        RefreshedAccessTokenResponseDto refreshedAccessTokenResponseDto = authService.refreshAccessToken(new RefreshTokenRequestDto(REFRESH_TOKEN));

        // then
        verify(refreshTokenRepository, times(1)).findByToken(REFRESH_TOKEN);
        verify(jwtUtility, times(1)).doGenerateToken(USER_NAME);
        assertEquals(REFRESHED_ACCESS_TOKEN, refreshedAccessTokenResponseDto.getRefreshedAccessToken());
        assertThrows(ResourceNotFoundException.class, () -> authService.refreshAccessToken(new RefreshTokenRequestDto(REFRESH_TOKEN_NOT_EXIST)));
    }

    @Test
    void signOut() {
        // given
        final String REFRESH_TOKEN = "refresh-token";
        final String REFRESH_TOKEN_NOT_EXIST = "refresh-token-not-exist";

        RefreshToken refreshToken = RefreshToken.builder()
                .token(REFRESH_TOKEN)
                .build();

        when(refreshTokenRepository.findByToken(REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));

        // when
        authService.signOut(new RefreshTokenRequestDto(REFRESH_TOKEN));
        authService.signOut(new RefreshTokenRequestDto(REFRESH_TOKEN_NOT_EXIST));

        // then
        verify(refreshTokenRepository, times(1)).delete(refreshToken);
    }
}