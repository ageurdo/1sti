package com.ageurdo.demo_user_auth_api.web.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDto {
    @CPF(message = "CPF Invalid")
    private String cpf;

    @NotBlank
    @Size(min = 8, max = 8)
    private String password;

    @NotBlank
    private String name;

    @DateTimeFormat
    private LocalDateTime dateOfBirth;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @NotBlank
    private String complement;

    @NotBlank
    private String neighborhood;

    @NotBlank
    private String city;

    @NotBlank @Size(min = 2)
    private String state;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String status;

    @DateTimeFormat
    private LocalDateTime createdAt;

    @NotBlank
    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private LocalDateTime deletedAt;

    private String deletedBy;
}
