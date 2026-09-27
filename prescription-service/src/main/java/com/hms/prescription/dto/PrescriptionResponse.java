package com.hms.prescription.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionResponse {

    private Long prescriptionId;

    private Long patientId;

    private Long doctorId;

    private LocalDate prescriptionDate;

    private String diagnosis;

    private String notes;

    private String status;
}