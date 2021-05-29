package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.Activity;
import com.emrecalik.wikimed.server.domain.User;
import com.emrecalik.wikimed.server.exception.ResourceNotFoundException;
import com.emrecalik.wikimed.server.model.response.UserDetailsResponseDto;
import com.emrecalik.wikimed.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByUserName() {
        // given
        final String USER_NAME = "emreboun";
        final String NOT_FOUND_USER_NAME = "emrebounnnn";

        User user = User.builder()
                .userName(USER_NAME)
                .build();

        Mockito.when(userRepository.findByUserName(USER_NAME)).thenReturn(Optional.of(user));

        // when
        User userFound = userService.findByUserName(USER_NAME);

        // then
        verify(userRepository, times(1)).findByUserName(USER_NAME);
        assertEquals(USER_NAME, userFound.getUserName());
        assertThrows(ResourceNotFoundException.class, () -> userService.findByUserName(NOT_FOUND_USER_NAME));
    }

    @Test
    void getUserById() {
        // given
        final long USER_ID = 1;
        final long USER_ID_NOT_FOUND = 99;

        User user = User.builder()
                .id(USER_ID)
                .build();

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        // when
        User userFound = userService.getUserById(USER_ID);

        // then
        verify(userRepository, times(1)).findById(USER_ID);
        assertEquals(USER_ID, userFound.getId());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(USER_ID_NOT_FOUND));
    }

    @Test
    void getUserDetails() {
        // given
        final long USER_ID = 1;
        final long REQUESTER_ID = 2;
        final long NOT_FOLLOWING_REQUESTER_ID = 99;

        User user = User.builder()
                .id(USER_ID)
                .rates(new HashSet<>())
                .followees(new HashSet<>())
                .build();

        User follower = User.builder()
                .id(REQUESTER_ID)
                .followees(Set.of(user))
                .build();

        user.setFollowers(Set.of(follower));

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        // when
        UserDetailsResponseDto userDetails = userService.getUserDetails(USER_ID, REQUESTER_ID);
        UserDetailsResponseDto userDetailsNotFollowedByRequester = userService.getUserDetails(USER_ID, NOT_FOLLOWING_REQUESTER_ID);

        // then
        assertTrue(userDetails.isFollowedByRequester());
        assertEquals(USER_ID, userDetails.getId());
        assertFalse(userDetailsNotFollowedByRequester.isFollowedByRequester());
        assertEquals(USER_ID, userDetailsNotFollowedByRequester.getId());
    }

    @Test
    void followUser() {
        // given
        final long USER_ID = 1;
        final long FOLLOWEE_ID = 2;

        User user = User.builder()
                .id(USER_ID)
                .build();

        User followee = User.builder()
                .id(FOLLOWEE_ID)
                .followers(Set.of(user))
                .followees(new HashSet<>())
                .rates(new HashSet<>())
                .build();

        user.setFollowees(new HashSet<>(Collections.singletonList(user)));

        when(userRepository.getOne(USER_ID)).thenReturn(user);
        when(userRepository.getOne(FOLLOWEE_ID)).thenReturn(followee);
        when(userRepository.findById(FOLLOWEE_ID)).thenReturn(Optional.of(followee));

        // when
        UserDetailsResponseDto userDetails = userService.followUser(USER_ID, FOLLOWEE_ID);

        // then
        verify(userRepository, times(1)).getOne(USER_ID);
        verify(userRepository, times(1)).getOne(FOLLOWEE_ID);
        verify(activityService, times(1)).saveActivity(any(Activity.class));
        verify(userRepository, times(1)).save(user);
        assertEquals(FOLLOWEE_ID, userDetails.getId());
        assertTrue(userDetails.getFollowers().stream()
                .anyMatch(userFollower -> userFollower.getId().equals(USER_ID)));
    }
}