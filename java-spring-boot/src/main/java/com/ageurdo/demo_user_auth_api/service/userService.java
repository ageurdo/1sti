package com.ageurdo.demo_user_auth_api.service;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class userService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
}
