package com.emrecalik.swe573.server.service.mapper;

import com.emrecalik.swe573.server.domain.Rate;
import com.emrecalik.swe573.server.domain.User;
import com.emrecalik.swe573.server.model.request.SignUpRequestDto;
import com.emrecalik.swe573.server.model.response.ArticleResponseDto;
import com.emrecalik.swe573.server.model.response.UserDetailsResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static User convertSignUpUserRequestToUser(SignUpRequestDto signUpRequestDto,
                                                      PasswordEncoder passwordEncoder) {
        return User.builder()
                .firstName(signUpRequestDto.getFirstName())
                .lastName(signUpRequestDto.getLastName())
                .userName(signUpRequestDto.getUserName())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build();
    }

    public static UserDetailsResponseDto convertUserToUserDetailsResponseDto(User user) {
        Set<ArticleResponseDto> userRatedArticles = user.getRates()
                .stream()
                .map(Rate::getArticle)
                .map(article -> ArticleMapper.convertArticleToArticleResponseDto(article, user.getId()))
                .collect(Collectors.toSet());

        return UserDetailsResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .ratedArticles(userRatedArticles)
                .build();
    }
}
