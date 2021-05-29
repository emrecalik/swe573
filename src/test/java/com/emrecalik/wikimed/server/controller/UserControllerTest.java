package com.emrecalik.wikimed.server.controller;

import com.emrecalik.wikimed.server.model.response.UserDetailsResponseDto;
import com.emrecalik.wikimed.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private final long USER_ID = 1;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUserDetails() throws Exception {
        // given
        final long REQUESTER_ID = 2;

        UserDetailsResponseDto userDetails = UserDetailsResponseDto.builder()
                .id(USER_ID)
                .build();

        Mockito.when(userService.getUserDetails(USER_ID, REQUESTER_ID)).thenReturn(userDetails);

        // when
        ResultActions response = mockMvc.perform(
                get(UserController.BASE_URL + "/" + USER_ID)
                        .param("requesterId", String.valueOf(REQUESTER_ID))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.id").value(USER_ID));
        verify(userService, times(1)).getUserDetails(USER_ID, REQUESTER_ID);
    }

    @Test
    void followUser() throws Exception {
        // given
        final long FOLLOWEE_ID = 2L;

        UserDetailsResponseDto userDetails = UserDetailsResponseDto.builder()
                .id(USER_ID)
                .build();

        when(userService.followUser(USER_ID, FOLLOWEE_ID)).thenReturn(userDetails);

        // when
        ResultActions response = mockMvc.perform(
                post(UserController.BASE_URL + "/" + USER_ID + "/follow/" + FOLLOWEE_ID)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.id").value(USER_ID));
        verify(userService, times(1)).followUser(USER_ID, FOLLOWEE_ID);
    }

    @Test
    void unFollowUser() throws Exception {
        // given
        final long FOLLOWEE_ID = 2L;

        UserDetailsResponseDto userDetails = UserDetailsResponseDto.builder()
                .id(USER_ID)
                .build();

        when(userService.unFollowUser(USER_ID, FOLLOWEE_ID)).thenReturn(userDetails);

        // when
        ResultActions response = mockMvc.perform(
                delete(UserController.BASE_URL + "/" + USER_ID + "/unFollow/" + FOLLOWEE_ID)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.id").value(USER_ID));
        verify(userService, times(1)).unFollowUser(USER_ID, FOLLOWEE_ID);
    }
}