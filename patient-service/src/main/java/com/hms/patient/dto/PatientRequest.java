package com.hms.patient.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequest {

    @NotBlank(message = "name is required")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "gender is required")
    private String gender;

    @NotNull(message = "dob is required")
    @Past(message = "dob must be in the past")
    private LocalDate dob;

    @NotBlank(message = "phone is required")
    @Size(max = 20)
    private String phone;

    @Email(message = "email must be valid")
    private String email;

    @Size(max = 255)
    private String address;

    @Size(max = 5)
    private String bloodGroup;
}
