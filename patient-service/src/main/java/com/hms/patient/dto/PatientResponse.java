package com.hms.patient.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponse {
    private Long patientId;
    private String name;
    private String gender;
    private LocalDate dob;
    private String phone;
    private String email;
    private String address;
    private String bloodGroup;
}
