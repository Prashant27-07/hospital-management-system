package com.hms.appointment.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {

    @NotNull(message = "patientId is required")
    private Long patientId;

    @NotNull(message = "doctorId is required")
    private Long doctorId;

    @NotNull(message = "appointmentDate is required")
    @FutureOrPresent(message = "appointmentDate cannot be in the past")
    private LocalDate appointmentDate;

    @NotNull(message = "appointmentTime is required")
    private LocalTime appointmentTime;

    @Size(max = 255)
    private String reason;

    @NotBlank
    private String status;
}
