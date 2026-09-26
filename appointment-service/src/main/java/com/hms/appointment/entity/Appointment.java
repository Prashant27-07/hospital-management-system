package com.hms.appointment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

// NOTE: patientId / doctorId are plain foreign-key values, NOT @ManyToOne relations.
// This service does not own Patient or Doctor data -- those live in patient-service
// and doctor-service. Cross-service lookups happen over REST; cross-service side
// effects happen over Kafka events (see AppointmentEventProducer).
@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(length = 255)
    private String reason;

    @Column(length = 20)
    private String status;
}
