package com.ageurdo.demo_user_auth_api.service;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.exception.CpfUniqueViolationException;
import com.ageurdo.demo_user_auth_api.exception.EntityNotFoundException;
import com.ageurdo.demo_user_auth_api.exception.PasswordException;
import com.ageurdo.demo_user_auth_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class userService {

    private final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        try {
            return userRepository.save(user);
        }
        catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(String.format("CPF {%s} já cadastrado", user.getCpf()));
        }

    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional
    public User changePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        try {
            User user = getById(id);

            validateCurrentPassword(user, currentPassword);
            validateNewPassword(id, newPassword, confirmPassword);

            user.setPassword(newPassword);
            return user;
        }

        catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Erro ao alterar senha");
        }
    }

    private void validateCurrentPassword(User user, String currentPassword) {
        if (!user.getPassword().equals(currentPassword)) {
            throw new PasswordException("Sua senha está incorreta");
        }
    }

    private void validateNewPassword(Long id, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordException("Nova senha não é igual a confirmação de senha");
        }

        if (newPassword.equals(getById(id).getPassword())) {
            throw new PasswordException("Escolha uma senha diferente da atual");
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User update(Long id, User user) {
        try {
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
            userFound.setUpdatedAt(LocalDateTime.now());
            userFound.setUpdatedBy("Usuário que virá no request");
            return userFound;
        }

        catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Erro ao alterar dados do usuário");
        }



    }
}
