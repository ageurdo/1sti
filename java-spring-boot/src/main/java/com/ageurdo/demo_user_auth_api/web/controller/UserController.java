package com.ageurdo.demo_user_auth_api.web.controller;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        User userFound = userService.getById(id);
        return ResponseEntity.ok(userFound);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user){
        User userChanged = userService.changePassword(id, user.getPassword());
        return ResponseEntity.ok(userChanged);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
}
