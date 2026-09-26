package com.hms.patient.service;

import com.hms.patient.dto.PatientRequest;
import com.hms.patient.dto.PatientResponse;
import com.hms.patient.entity.Patient;
import com.hms.patient.exception.ResourceNotFoundException;
import com.hms.patient.mapper.PatientMapper;
import com.hms.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientResponse create(PatientRequest request) {
        Patient patient = patientMapper.toEntity(request);
        Patient saved = patientRepository.save(patient);
        return patientMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> findAll() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PatientResponse findById(Long id) {
        Patient patient = getOrThrow(id);
        return patientMapper.toResponse(patient);
    }

    public PatientResponse update(Long id, PatientRequest request) {
        Patient patient = getOrThrow(id);
        patientMapper.updateEntity(patient, request);
        return patientMapper.toResponse(patientRepository.save(patient));
    }

    public void delete(Long id) {
        Patient patient = getOrThrow(id);
        patientRepository.delete(patient);
    }

    private Patient getOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
    }
}
