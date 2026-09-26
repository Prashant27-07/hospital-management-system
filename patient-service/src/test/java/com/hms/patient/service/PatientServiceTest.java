package com.hms.patient.service;

import com.hms.patient.dto.PatientRequest;
import com.hms.patient.dto.PatientResponse;
import com.hms.patient.entity.Patient;
import com.hms.patient.exception.ResourceNotFoundException;
import com.hms.patient.mapper.PatientMapper;
import com.hms.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private PatientRequest request;

    @BeforeEach
    void setUp() {
        patient = Patient.builder()
                .patientId(1L)
                .name("Prashant Sharma")
                .gender("MALE")
                .dob(LocalDate.of(1998, 5, 20))
                .phone("9999999999")
                .email("test@example.com")
                .bloodGroup("O+")
                .build();

        request = PatientRequest.builder()
                .name("Prashant Sharma")
                .gender("MALE")
                .dob(LocalDate.of(1998, 5, 20))
                .phone("9999999999")
                .email("test@example.com")
                .bloodGroup("O+")
                .build();
    }

    @Test
    void create_savesAndReturnsResponse() {
        when(patientMapper.toEntity(request)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toResponse(patient)).thenReturn(
                PatientResponse.builder().patientId(1L).name("Prashant Sharma").build());

        PatientResponse response = patientService.create(request);

        assertThat(response.getPatientId()).isEqualTo(1L);
        verify(patientRepository).save(patient);
    }

    @Test
    void findById_notFound_throws() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void delete_existingPatient_removesIt() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        patientService.delete(1L);

        verify(patientRepository).delete(patient);
    }
}
