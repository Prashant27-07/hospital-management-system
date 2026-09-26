package com.hms.doctor.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRequest {

    @NotNull(message = "departmentId is required")
    private Long departmentId;

    @NotBlank(message = "name is required")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "specialization is required")
    private String specialization;

    private String qualification;

    @PositiveOrZero
    private Integer experience;

    @NotBlank
    private String phone;

    @Email
    private String email;

    @NotNull
    @Positive(message = "consultationFee must be positive")
    private BigDecimal consultationFee;

    @NotBlank
    private String status;
}
