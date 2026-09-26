package com.hms.patient.mapper;

import com.hms.patient.dto.PatientRequest;
import com.hms.patient.dto.PatientResponse;
import com.hms.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequest request) {
        return Patient.builder()
                .name(request.getName())
                .gender(request.getGender())
                .dob(request.getDob())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .bloodGroup(request.getBloodGroup())
                .build();
    }

    public void updateEntity(Patient patient, PatientRequest request) {
        patient.setName(request.getName());
        patient.setGender(request.getGender());
        patient.setDob(request.getDob());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());
    }

    public PatientResponse toResponse(Patient patient) {
        return PatientResponse.builder()
                .patientId(patient.getPatientId())
                .name(patient.getName())
                .gender(patient.getGender())
                .dob(patient.getDob())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .bloodGroup(patient.getBloodGroup())
                .build();
    }
}
