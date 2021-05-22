package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.Activity;
import com.emrecalik.wikimed.server.domain.User;
import com.emrecalik.wikimed.server.exception.BadRequestException;
import com.emrecalik.wikimed.server.exception.ResourceNotFoundException;
import com.emrecalik.wikimed.server.model.response.UserDetailsResponseDto;
import com.emrecalik.wikimed.server.repository.UserRepository;
import com.emrecalik.wikimed.server.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final String NOT_FOUND_USER_FOR_USERNAME = "Not found user for username =  ";

    private static final String NOT_FOUND_USER_FOR_ID = "Not found user for id = ";

    private static final String USER_CAN_NOT_FOLLOW_HIM_OR_HER_SELF = "User can not follow him/herself.";

    private static final String USER_CAN_NOT_UNFOLLOW_HIM_OR_HER_SELF = "User can not unfollow him/herself.";

    private final UserRepository userRepository;

    private final ActivityService activityService;

    public UserServiceImpl(UserRepository userRepository, ActivityService activityService) {
        this.userRepository = userRepository;
        this.activityService = activityService;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException(UserServiceImpl.NOT_FOUND_USER_FOR_USERNAME + userName));
    }

    @Override
    public boolean existByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserProxyById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UserServiceImpl.NOT_FOUND_USER_FOR_ID + id));
    }

    @Override
    public UserDetailsResponseDto getUserDetails(Long userId, Long requesterId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(UserServiceImpl.NOT_FOUND_USER_FOR_ID + userId));

        boolean isFollowedByRequester = user.getFollowers().stream()
                .anyMatch(followee -> followee.getId().equals(requesterId));

        return UserMapper.convertUserToUserDetailsResponseDto(user, isFollowedByRequester);
    }

    @Override
    public UserDetailsResponseDto followUser(Long userId, Long followeeId) {
        if (userId.equals(followeeId)) {
            throw new BadRequestException(UserServiceImpl.USER_CAN_NOT_FOLLOW_HIM_OR_HER_SELF);
        }
        User user = getUserProxyById(userId);
        User followee = getUserProxyById(followeeId);
        user.getFollowees().add(followee);
        userRepository.save(user);

        Activity activity = Activity.builder()
                .summary(" followed ")
                .activityType(Activity.ActivityType.FOLLOW)
                .objectType(Activity.ObjectType.USER)
                .actorId(userId)
                .objectId(followeeId)
                .build();
        activityService.saveActivity(activity);

        return getUserDetails(followeeId, userId);
    }

    @Override
    public UserDetailsResponseDto unFollowUser(Long userId, Long followeeId) {
        if (userId.equals(followeeId)) {
            throw new BadRequestException(UserServiceImpl.USER_CAN_NOT_UNFOLLOW_HIM_OR_HER_SELF);
        }
        User user = getUserProxyById(userId);
        User followee = getUserProxyById(followeeId);
        user.getFollowees().remove(followee);
        userRepository.save(user);

        Activity activity = Activity.builder()
                .summary(" unfollowed ")
                .activityType(Activity.ActivityType.UNFOLLOW)
                .objectType(Activity.ObjectType.USER)
                .actorId(userId)
                .objectId(followeeId)
                .build();
        activityService.saveActivity(activity);

        return getUserDetails(followeeId, userId);
    }
}
