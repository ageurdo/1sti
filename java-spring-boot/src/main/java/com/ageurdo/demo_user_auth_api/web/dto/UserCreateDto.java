package com.ageurdo.demo_user_auth_api.web.dto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    private String status;

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

//    @Nullable
//    private String createdBy;

//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private String updatedBy;
//    private LocalDateTime deletedAt;
//    private String deletedBy;

}
