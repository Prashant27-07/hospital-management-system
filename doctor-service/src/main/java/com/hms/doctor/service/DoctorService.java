package com.hms.doctor.service;

import com.hms.doctor.dto.DoctorRequest;
import com.hms.doctor.dto.DoctorResponse;
import com.hms.doctor.entity.Doctor;
import com.hms.doctor.exception.ResourceNotFoundException;
import com.hms.doctor.mapper.DoctorMapper;
import com.hms.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorResponse create(DoctorRequest request) {
        Doctor doctor = doctorMapper.toEntity(request);
        return doctorMapper.toResponse(doctorRepository.save(doctor));
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> findAll() {
        return doctorRepository.findAll().stream().map(doctorMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DoctorResponse findById(Long id) {
        return doctorMapper.toResponse(getOrThrow(id));
    }

    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor doctor = getOrThrow(id);
        doctorMapper.updateEntity(doctor, request);
        return doctorMapper.toResponse(doctorRepository.save(doctor));
    }

    public void delete(Long id) {
        doctorRepository.delete(getOrThrow(id));
    }

    private Doctor getOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));
    }
}
