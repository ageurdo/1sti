package com.ageurdo.demo_user_auth_api.web.controller;

import com.ageurdo.demo_user_auth_api.jwt.JwtToken;
import com.ageurdo.demo_user_auth_api.jwt.JwtUserDetailsService;
import com.ageurdo.demo_user_auth_api.service.UserService;
import com.ageurdo.demo_user_auth_api.web.dto.UserLoginDto;
import com.ageurdo.demo_user_auth_api.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto dto, HttpServletRequest request){
        log.info("Processo de autenticação pelo login {}", dto.getCpf());
        try {
        // Monta um objeto com os dados de cpf e senha que chegam no request
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getCpf(), dto.getPassword());
        // Verifica se existe um usuário com este cpf e senha no banco
            authenticationManager.authenticate((authenticationToken));
        // Se existe no banco gera um token e retorna
            JwtToken token = detailsService.getTokenAuthenticated(dto.getCpf());

            return ResponseEntity.ok(token);
        }
        catch (AuthenticationException ex){
        // Caso o usuário não exista cairá neste catch retornand o erro
            log.warn("Bad credentials from Cpf '{}'", dto.getCpf(), ex);
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}
