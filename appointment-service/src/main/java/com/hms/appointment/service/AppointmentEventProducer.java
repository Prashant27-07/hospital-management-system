package com.hms.appointment.service;

import com.hms.appointment.config.KafkaTopicConfig;
import com.hms.appointment.event.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentEventProducer {

    private final KafkaTemplate<String, AppointmentCreatedEvent> kafkaTemplate;

    public void publishAppointmentCreated(AppointmentCreatedEvent event) {
        kafkaTemplate.send(KafkaTopicConfig.APPOINTMENT_EVENTS_TOPIC,
                        String.valueOf(event.appointmentId()), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish AppointmentCreatedEvent for appointmentId={}",
                                event.appointmentId(), ex);
                    } else {
                        log.info("Published AppointmentCreatedEvent for appointmentId={}", event.appointmentId());
                    }
                });
    }
}
