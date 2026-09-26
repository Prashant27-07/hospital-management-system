package com.hms.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "username is required")
    @Size(min = 3, max = 100)
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

    @NotBlank(message = "role is required")
    @Pattern(regexp = "ADMIN|DOCTOR|RECEPTIONIST|NURSE|LAB_TECHNICIAN|PHARMACIST|ACCOUNTANT",
            message = "role must be one of ADMIN, DOCTOR, RECEPTIONIST, NURSE, LAB_TECHNICIAN, PHARMACIST, ACCOUNTANT")
    private String role;
}
