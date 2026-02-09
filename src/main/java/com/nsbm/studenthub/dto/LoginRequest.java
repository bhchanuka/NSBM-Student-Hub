package com.nsbm.studenthub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO for User Login Request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
