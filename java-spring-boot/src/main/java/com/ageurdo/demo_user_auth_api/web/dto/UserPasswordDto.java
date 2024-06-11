package com.ageurdo.demo_user_auth_api.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDto {
    private String currentPassword;

    @NotBlank
    @Size(min = 8, max = 8)
    private String newPassword;

    @NotBlank
    @Size(min = 8, max = 8)
    private String confirmPassword;
}
