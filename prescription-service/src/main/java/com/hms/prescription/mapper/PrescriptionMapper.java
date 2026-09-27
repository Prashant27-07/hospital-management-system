package com.hms.prescription.mapper;

import com.hms.prescription.dto.PrescriptionRequest;
import com.hms.prescription.dto.PrescriptionResponse;
import com.hms.prescription.entity.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    public Prescription toEntity(PrescriptionRequest request) {
        return Prescription.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .prescriptionDate(request.getPrescriptionDate())
                .diagnosis(request.getDiagnosis())
                .notes(request.getNotes())
                .status(request.getStatus() == null || request.getStatus().isBlank()
                        ? "ACTIVE"
                        : request.getStatus())
                .build();
    }

    public PrescriptionResponse toResponse(Prescription prescription) {
        return PrescriptionResponse.builder()
                .prescriptionId(prescription.getPrescriptionId())
                .patientId(prescription.getPatientId())
                .doctorId(prescription.getDoctorId())
                .prescriptionDate(prescription.getPrescriptionDate())
                .diagnosis(prescription.getDiagnosis())
                .notes(prescription.getNotes())
                .status(prescription.getStatus())
                .build();
    }

    public void updateEntity(
            Prescription prescription,
            PrescriptionRequest request) {

        prescription.setPatientId(request.getPatientId());
        prescription.setDoctorId(request.getDoctorId());
        prescription.setPrescriptionDate(request.getPrescriptionDate());
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setNotes(request.getNotes());

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            prescription.setStatus(request.getStatus());
        }
    }
}