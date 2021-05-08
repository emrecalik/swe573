package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.RefreshToken;
import com.emrecalik.swe573.server.domain.Role;
import com.emrecalik.swe573.server.domain.User;
import com.emrecalik.swe573.server.exception.BadRequestException;
import com.emrecalik.swe573.server.exception.ResourceNotFoundException;
import com.emrecalik.swe573.server.model.request.RefreshTokenRequestDto;
import com.emrecalik.swe573.server.model.request.SignInRequestDto;
import com.emrecalik.swe573.server.model.request.SignUpRequestDto;
import com.emrecalik.swe573.server.model.response.ApiResponseDto;
import com.emrecalik.swe573.server.model.response.RefreshedAccessTokenResponseDto;
import com.emrecalik.swe573.server.model.response.SignInResponseDto;
import com.emrecalik.swe573.server.repository.RefreshTokenRepository;
import com.emrecalik.swe573.server.security.JwtUtility;
import com.emrecalik.swe573.server.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtility jwtUtility;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RoleService roleService;

    public AuthServiceImpl(UserService userService, AuthenticationManager authenticationManager,
                           JwtUtility jwtUtility, PasswordEncoder passwordEncoder,
                           RefreshTokenRepository refreshTokenRepository, RoleService roleService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.roleService = roleService;
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                signInRequestDto.getUserName(), signInRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtility.generateToken(authentication);
        User user = userService.findByUserName(signInRequestDto.getUserName());
        String userRole = user.getRole().getRoleName().name();
        userRole = userRole.substring(userRole.indexOf("_") + 1);
        String refreshToken = createRefreshToken(user);
        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .role(userRole)
                .build();
    }

    @Override
    public ApiResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        String signUpUserName = signUpRequestDto.getUserName();
        if (userService.existByUserName(signUpUserName)) {
            throw new BadRequestException("Username = " + signUpUserName + " has already been taken!");
        }

        String signUpEmail = signUpRequestDto.getEmail();
        if (userService.existByEmail(signUpEmail)) {
            throw new BadRequestException("Email = " + signUpEmail + " has already been taken!");
        }

        User userToSave = UserMapper.convertSignUpUserRequestToUser(signUpRequestDto, passwordEncoder);
        Role userRole = roleService.getRoleByRoleName(Role.RoleName.ROLE_USER);
        userToSave.setRole(userRole);
        userService.save(userToSave);
        return ApiResponseDto.builder()
                .header("Success")
                .message("User is successfully registered.")
                .build();
    }

    @Override
    public RefreshedAccessTokenResponseDto refreshAccessToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new ResourceNotFoundException("Refresh Token does not exits!"));

        String refreshedAccessToken = jwtUtility.doGenerateToken(refreshToken.getUser().getUserName());

        return RefreshedAccessTokenResponseDto.builder()
                .refreshedAccessToken(refreshedAccessToken)
                .build();
    }

    private String createRefreshToken(User user) {
        String refreshTokenCreated = RandomStringUtils.randomAlphanumeric(128);
        boolean doesExist = refreshTokenRepository.existsByUserId(user.getId());
        if (doesExist) {
            RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId());
            refreshToken.setToken(refreshTokenCreated);
            refreshTokenRepository.save(refreshToken);
        } else {
            refreshTokenRepository.save(RefreshToken.builder()
                    .token(refreshTokenCreated)
                    .user(user)
                    .build());
        }
        return refreshTokenCreated;
    }

    @Override
    public void signOut(RefreshTokenRequestDto refreshTokenRequestDto) {
        refreshTokenRepository.findByToken(refreshTokenRequestDto.getRefreshToken())
                .ifPresent(refreshTokenRepository::delete);
        log.info("Deleted Refresh Token: " + refreshTokenRequestDto.getRefreshToken());
    }
}
