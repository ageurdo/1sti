package com.ageurdo.demo_user_auth_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDto {
    @NotBlank
    @CPF(message="Formato do Cpf é inválido")
    private String cpf;

    @NotBlank
    @Size(min= 8, max=8)
    private String password;
}
