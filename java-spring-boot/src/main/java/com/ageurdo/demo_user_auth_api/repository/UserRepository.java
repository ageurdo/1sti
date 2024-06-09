package com.ageurdo.demo_user_auth_api.repository;

import com.ageurdo.demo_user_auth_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}