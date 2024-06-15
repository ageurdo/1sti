package com.ageurdo.demo_user_auth_api.repository;

import com.ageurdo.demo_user_auth_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByCpf(String cpf);

    @Query("select u.role from User u where u.cpf like :cpf")
    User.Role getRoleByCpf(String cpf);
}