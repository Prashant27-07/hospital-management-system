package com.hms.prescription.service;

import com.hms.prescription.dto.PrescriptionRequest;
import com.hms.prescription.dto.PrescriptionResponse;
import com.hms.prescription.entity.Prescription;
import com.hms.prescription.exception.ResourceNotFoundException;
import com.hms.prescription.mapper.PrescriptionMapper;
import com.hms.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    public PrescriptionResponse create(PrescriptionRequest request) {
        Prescription prescription = prescriptionMapper.toEntity(request);
        Prescription saved = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> findAll() {
        return prescriptionRepository.findAll()
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PrescriptionResponse findById(Long id) {
        return prescriptionMapper.toResponse(getOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> findByPatientId(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId)
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> findByDoctorId(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId)
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    public PrescriptionResponse update(
            Long id,
            PrescriptionRequest request) {

        Prescription prescription = getOrThrow(id);

        prescriptionMapper.updateEntity(prescription, request);

        return prescriptionMapper.toResponse(
                prescriptionRepository.save(prescription)
        );
    }

    public void delete(Long id) {
        Prescription prescription = getOrThrow(id);
        prescriptionRepository.delete(prescription);
    }

    private Prescription getOrThrow(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Prescription not found with id " + id
                        ));
    }
}