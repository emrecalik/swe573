package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.model.response.UserDetailsResponseDto;
import com.emrecalik.swe573.server.domain.User;

public interface UserService {
    User findByUserName(String userName);
    boolean existByUserName(String userName);
    boolean existByEmail(String email);
    void save(User user);
    User getUserProxy(Long id);
    UserDetailsResponseDto getUserDetails(Long id);
}
