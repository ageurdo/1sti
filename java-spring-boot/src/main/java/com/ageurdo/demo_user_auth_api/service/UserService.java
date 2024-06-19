package com.ageurdo.demo_user_auth_api.service;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.exception.CpfUniqueViolationException;
import com.ageurdo.demo_user_auth_api.exception.EntityNotFoundException;
import com.ageurdo.demo_user_auth_api.exception.PasswordException;
import com.ageurdo.demo_user_auth_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class
UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    AuthenticationPrincipal authenticationPrincipal;
    @Transactional
    public User create(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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
            if (!newPassword.equals(confirmPassword)) {
                throw new PasswordException("Nova senha não é igual a confirmação de senha");
            }

            User user = getById(id);

            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new PasswordException("Escolha uma senha diferente da atual");
            }

            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new PasswordException("Sua senha está incorreta");
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            return user;
        }

        catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Erro ao alterar senha");
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

        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Erro ao alterar dados do usuário");
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }



    }

    @Transactional
    public void deleteById(Long id) {
        try{
            String userLogged = ""; //authenticationPrincipal.getClass().getName();
            User user = getById(id);

            if (user.getRole() == User.Role.ROLE_ADMIN) {
                throw new AccessDeniedException("Usuário administrador não pode ser removido");
            }

            user.setDeletedBy(userLogged);
            user.setDeletedAt(LocalDateTime.now());
            user.setStatus(User.RecordStatus.REMOVED);

            return;
        }
        catch(RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }

    }
    @Transactional(readOnly = true)
    public User getByCpf(String cpf) {
        User user = userRepository.getByCpf(cpf).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com cpf=%s não encontrado", cpf))
        );

        if (user.getStatus() == User.RecordStatus.REMOVED) {
            throw new EntityNotFoundException("Usuário inativo");
        }

        return user;
    }

    @Transactional(readOnly = true)
    public User.Role getRoleByCpf(String cpf) {
        return userRepository.getRoleByCpf(cpf);
    }



}
