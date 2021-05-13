package com.emrecalik.swe573.server.service.mapper;

import com.emrecalik.swe573.server.domain.Rate;
import com.emrecalik.swe573.server.domain.User;
import com.emrecalik.swe573.server.model.request.SignUpRequestDto;
import com.emrecalik.swe573.server.model.response.ArticleResponseDto;
import com.emrecalik.swe573.server.model.response.FollowingDetailsResponseDto;
import com.emrecalik.swe573.server.model.response.UserDetailsResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
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

    public static UserDetailsResponseDto convertUserToUserDetailsResponseDto(User user,
                                                                             boolean isFollowedByRequester) {
        Set<ArticleResponseDto> userRatedArticles = user.getRates()
                .stream()
                .map(Rate::getArticle)
                .map(article -> ArticleMapper.convertArticleToArticleResponseDto(article, user.getId()))
                .collect(Collectors.toSet());

        Set<FollowingDetailsResponseDto> followees = new HashSet<>();
        for (User followee : user.getFollowees()) {
            followees.add(FollowingDetailsResponseDto.builder()
                    .id(followee.getId())
                    .firstName(followee.getFirstName())
                    .lastName(followee.getLastName())
                    .userName(followee.getUserName())
                    .email(followee.getEmail())
                    .build());
        }

        Set<FollowingDetailsResponseDto> followers = new HashSet<>();
        for (User follower : user.getFollowers()) {
            followers.add(FollowingDetailsResponseDto.builder()
                    .id(follower.getId())
                    .firstName(follower.getFirstName())
                    .lastName(follower.getLastName())
                    .userName(follower.getUserName())
                    .email(follower.getEmail())
                    .build());
        }

        return UserDetailsResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .ratedArticles(userRatedArticles)
                .followees(followees)
                .followers(followers)
                .isFollowedByRequester(isFollowedByRequester)
                .build();
    }
}
