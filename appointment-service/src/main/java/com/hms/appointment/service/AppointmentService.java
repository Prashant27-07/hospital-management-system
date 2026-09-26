package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentRequest;
import com.hms.appointment.dto.AppointmentResponse;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.event.AppointmentCreatedEvent;
import com.hms.appointment.exception.ResourceNotFoundException;
import com.hms.appointment.mapper.AppointmentMapper;
import com.hms.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentEventProducer eventProducer;

    public AppointmentResponse create(AppointmentRequest request) {
        Appointment appointment = appointmentMapper.toEntity(request);
        Appointment saved = appointmentRepository.save(appointment);

        // Fire-and-forget async notification via Kafka -- appointment-service does not
        // wait on notification-service or audit-service to respond.
        eventProducer.publishAppointmentCreated(AppointmentCreatedEvent.of(
                saved.getAppointmentId(), saved.getPatientId(), saved.getDoctorId(),
                saved.getAppointmentDate(), saved.getAppointmentTime()));

        return appointmentMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAll() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponse findById(Long id) {
        return appointmentMapper.toResponse(getOrThrow(id));
    }

    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment appointment = getOrThrow(id);
        appointmentMapper.updateEntity(appointment, request);
        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    public void delete(Long id) {
        appointmentRepository.delete(getOrThrow(id));
    }

    private Appointment getOrThrow(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + id));
    }
}
