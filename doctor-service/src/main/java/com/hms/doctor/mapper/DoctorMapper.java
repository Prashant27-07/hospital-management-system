package com.hms.doctor.mapper;

import com.hms.doctor.dto.DoctorRequest;
import com.hms.doctor.dto.DoctorResponse;
import com.hms.doctor.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor toEntity(DoctorRequest request) {
        return Doctor.builder()
                .departmentId(request.getDepartmentId())
                .name(request.getName())
                .specialization(request.getSpecialization())
                .qualification(request.getQualification())
                .experience(request.getExperience())
                .phone(request.getPhone())
                .email(request.getEmail())
                .consultationFee(request.getConsultationFee())
                .status(request.getStatus())
                .build();
    }

    public void updateEntity(Doctor doctor, DoctorRequest request) {
        doctor.setDepartmentId(request.getDepartmentId());
        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperience(request.getExperience());
        doctor.setPhone(request.getPhone());
        doctor.setEmail(request.getEmail());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setStatus(request.getStatus());
    }

    public DoctorResponse toResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .doctorId(doctor.getDoctorId())
                .departmentId(doctor.getDepartmentId())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .qualification(doctor.getQualification())
                .experience(doctor.getExperience())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .consultationFee(doctor.getConsultationFee())
                .status(doctor.getStatus())
                .build();
    }
}
