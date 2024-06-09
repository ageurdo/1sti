package com.ageurdo.demo_user_auth_api.web.controller;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserRepository userRepository;
    private final com.ageurdo.demo_user_auth_api.service.userService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        User userSaved = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }
}
