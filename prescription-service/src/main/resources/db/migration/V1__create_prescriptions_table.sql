CREATE TABLE prescriptions (
    prescription_id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    prescription_date DATE NOT NULL,
    diagnosis VARCHAR(500),
    notes TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE'
);

CREATE INDEX idx_prescriptions_patient_id
    ON prescriptions(patient_id);

CREATE INDEX idx_prescriptions_doctor_id
    ON prescriptions(doctor_id);

CREATE INDEX idx_prescriptions_date
    ON prescriptions(prescription_date);