CREATE TABLE roles (
    role_id      BIGSERIAL PRIMARY KEY,
    role_name    VARCHAR(30) NOT NULL UNIQUE,
    description  VARCHAR(255)
);

CREATE TABLE users (
    user_id        BIGSERIAL PRIMARY KEY,
    username       VARCHAR(100) NOT NULL UNIQUE,
    password_hash  VARCHAR(255) NOT NULL,
    role           VARCHAR(30) NOT NULL,
    status         VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at     TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO roles (role_name, description) VALUES
    ('ADMIN', 'Full system access'),
    ('DOCTOR', 'Access to relevant patient, appointment and prescription data'),
    ('RECEPTIONIST', 'Manages appointments and patient registration'),
    ('NURSE', 'Ward and admission related access'),
    ('LAB_TECHNICIAN', 'Manages lab tests and reports'),
    ('PHARMACIST', 'Manages medicine inventory and prescriptions'),
    ('ACCOUNTANT', 'Manages billing and payments');
