package com.ageurdo.demo_user_auth_api.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserResponseDto {
    private Long id;
    private String cpf;
    private String role;
    private String name;
    private LocalDateTime dateOfBirth;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;
    private String status;
}
