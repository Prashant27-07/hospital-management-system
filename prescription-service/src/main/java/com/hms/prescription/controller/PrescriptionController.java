package com.hms.prescription.controller;

import com.hms.prescription.dto.PrescriptionRequest;
import com.hms.prescription.dto.PrescriptionResponse;
import com.hms.prescription.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
@Tag(name = "Prescriptions", description = "Prescription management")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    @Operation(summary = "Create a prescription")
    public ResponseEntity<PrescriptionResponse> create(
            @Valid @RequestBody PrescriptionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prescriptionService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> findAll() {
        return ResponseEntity.ok(prescriptionService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get prescription by ID")
    public ResponseEntity<PrescriptionResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                prescriptionService.findById(id)
        );
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get prescriptions by patient")
    public ResponseEntity<List<PrescriptionResponse>> findByPatientId(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                prescriptionService.findByPatientId(patientId)
        );
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get prescriptions by doctor")
    public ResponseEntity<List<PrescriptionResponse>> findByDoctorId(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                prescriptionService.findByDoctorId(doctorId)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a prescription")
    public ResponseEntity<PrescriptionResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionRequest request) {

        return ResponseEntity.ok(
                prescriptionService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a prescription")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        prescriptionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}