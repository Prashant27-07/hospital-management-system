CREATE TABLE patients (
    patient_id   BIGSERIAL PRIMARY KEY,
    name         VARCHAR(150) NOT NULL,
    gender       VARCHAR(10),
    dob          DATE,
    phone        VARCHAR(20),
    email        VARCHAR(150),
    address      VARCHAR(255),
    blood_group  VARCHAR(5)
);

CREATE INDEX idx_patients_phone ON patients(phone);
