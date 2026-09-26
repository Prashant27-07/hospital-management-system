package com.hms.appointment.event;

import java.time.LocalDate;
import java.time.LocalTime;

// Payload published to the "hospital.appointment.events" Kafka topic
// whenever a new appointment is booked. notification-service and
// audit-service both consume this topic independently.
public record AppointmentCreatedEvent(
        Long appointmentId,
        Long patientId,
        Long doctorId,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        String eventType
) {
    public static AppointmentCreatedEvent of(Long appointmentId, Long patientId, Long doctorId,
                                              LocalDate date, LocalTime time) {
        return new AppointmentCreatedEvent(appointmentId, patientId, doctorId, date, time, "APPOINTMENT_CREATED");
    }
}
