package com.ageurdo.demo_user_auth_api.service;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userService {

    private final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );
    }

    @Transactional
    public User changePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Nova senha não é igual a confirmação de senha");
        }

        User user = getById(id);
        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Sua senha esta incorreta");
        }

        if (user.getPassword().equals(newPassword)) {
            throw new RuntimeException("Escolha uma senha diferente da atual");
        }

        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User update(Long id, User user) {
        User userFound = getById(id);
        userFound.setName(user.getName());
        userFound.setDateOfBirth(user.getDateOfBirth());
        userFound.setStreet(user.getStreet());
        userFound.setNumber(user.getNumber());
        userFound.setComplement(user.getComplement());
        userFound.setNeighborhood(user.getNeighborhood());
        userFound.setCity(user.getCity());
        userFound.setState(user.getState());
        userFound.setZipCode(user.getZipCode());

        return userFound;

    }
}
