package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.User;
import com.emrecalik.swe573.server.exception.ResourceNotFoundException;
import com.emrecalik.swe573.server.model.response.UserDetailsResponseDto;
import com.emrecalik.swe573.server.repository.UserRepository;
import com.emrecalik.swe573.server.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found User For UserName = " + userName));
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
    public User getUserProxy(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public UserDetailsResponseDto getUserDetails(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found User for Id = " + id));
        return UserMapper.convertUserToUserDetailsResponseDto(user);
    }


}
