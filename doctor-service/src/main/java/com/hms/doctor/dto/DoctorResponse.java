package com.hms.doctor.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {
    private Long doctorId;
    private Long departmentId;
    private String name;
    private String specialization;
    private String qualification;
    private Integer experience;
    private String phone;
    private String email;
    private BigDecimal consultationFee;
    private String status;
}
