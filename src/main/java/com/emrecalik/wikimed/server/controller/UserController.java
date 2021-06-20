package com.emrecalik.wikimed.server.controller;

import com.emrecalik.wikimed.server.model.response.UserDetailsResponseDto;
import com.emrecalik.wikimed.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/api/users";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}", params = {"requesterId"})
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(@PathVariable Long userId,
                                                                 @RequestParam Long requesterId) {
        return ResponseEntity.ok(userService.getUserDetails(userId, requesterId));
    }

    @PostMapping("/{userId}/follow/{followeeId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDetailsResponseDto> followUser(@PathVariable Long userId,
                                                             @PathVariable Long followeeId) {
        return ResponseEntity.ok(userService.followUser(userId, followeeId));
    }

    @DeleteMapping("/{userId}/unFollow/{followeeId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDetailsResponseDto> unFollowUser(@PathVariable Long userId,
                                                               @PathVariable Long followeeId) {
        return ResponseEntity.ok(userService.unFollowUser(userId, followeeId));
    }
}
