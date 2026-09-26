CREATE TABLE appointments (
    appointment_id    BIGSERIAL PRIMARY KEY,
    patient_id        BIGINT NOT NULL,
    doctor_id         BIGINT NOT NULL,
    appointment_date  DATE NOT NULL,
    appointment_time  TIME NOT NULL,
    reason            VARCHAR(255),
    status            VARCHAR(20) NOT NULL
);

CREATE INDEX idx_appointments_patient_id ON appointments(patient_id);
CREATE INDEX idx_appointments_doctor_id ON appointments(doctor_id);
CREATE INDEX idx_appointments_date ON appointments(appointment_date);
