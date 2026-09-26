CREATE TABLE doctors (
    doctor_id          BIGSERIAL PRIMARY KEY,
    department_id      BIGINT NOT NULL,
    name               VARCHAR(150) NOT NULL,
    specialization     VARCHAR(100),
    qualification      VARCHAR(150),
    experience         INT,
    phone              VARCHAR(20),
    email              VARCHAR(150),
    consultation_fee   NUMERIC(10,2),
    status             VARCHAR(20)
);

CREATE INDEX idx_doctors_department_id ON doctors(department_id);
