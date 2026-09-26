# ER Diagram — Hospital Management System

```mermaid
erDiagram
    DEPARTMENT ||--o{ DOCTOR : employs
    DOCTOR ||--o{ SCHEDULE : has
    PATIENT ||--o{ APPOINTMENT : books
    DOCTOR ||--o{ APPOINTMENT : attends
    PATIENT ||--o{ PRESCRIPTION : has
    DOCTOR ||--o{ PRESCRIPTION : writes
    PRESCRIPTION ||--o{ PRESCRIPTION_ITEM : includes
    MEDICINE ||--o{ PRESCRIPTION_ITEM : includes
    PATIENT ||--o{ ADMISSION : has
    DOCTOR ||--o{ ADMISSION : supervises
    BED ||--o{ ADMISSION : assigned_to
    PATIENT ||--o{ BILLING : billed
    BILLING ||--o{ PAYMENT : generates
    PATIENT ||--o{ INSURANCE_CLAIM : submits
    BILLING ||--o{ INSURANCE_CLAIM : covers
    PATIENT ||--o{ LAB_TEST : undergoes
    LAB_TEST ||--|| LAB_REPORT : produces
    ROLES ||--o{ USERS : has
    USERS ||--o{ NOTIFICATION : has
    USERS ||--o{ AUDIT_LOG : has

    PATIENT {
        bigint patient_id PK
        string name
        string gender
        date dob
        string phone
        string email
        string address
        string blood_group
    }

    DOCTOR {
        bigint doctor_id PK
        bigint department_id FK
        string name
        string specialization
        string qualification
        int experience
        string phone
        string email
        decimal consultation_fee
        string status
    }

    DEPARTMENT {
        bigint department_id PK
        string department_name
        string description
    }

    APPOINTMENT {
        bigint appointment_id PK
        bigint patient_id FK
        bigint doctor_id FK
        date appointment_date
        time appointment_time
        string reason
        string status
    }

    SCHEDULE {
        bigint schedule_id PK
        bigint doctor_id FK
        string day_of_week
        time start_time
        time end_time
        int slot_duration
    }

    PRESCRIPTION {
        bigint prescription_id PK
        bigint patient_id FK
        bigint doctor_id FK
        date prescription_date
        string notes
    }

    PRESCRIPTION_ITEM {
        bigint item_id PK
        bigint prescription_id FK
        bigint medicine_id FK
        string dosage
        string duration
        string frequency
    }

    MEDICINE {
        bigint medicine_id PK
        string medicine_name
        string manufacturer
        decimal price
        int stock
        string description
    }

    ADMISSION {
        bigint admission_id PK
        bigint patient_id FK
        bigint doctor_id FK
        bigint bed_id FK
        date admission_date
        date discharge_date
        string status
        string notes
    }

    BED {
        bigint bed_id PK
        string bed_number
        string ward
        string bed_type
        decimal charge_per_day
        string status
    }

    BILLING {
        bigint bill_id PK
        bigint patient_id FK
        date bill_date
        decimal total_amount
        decimal discount
        decimal tax
        decimal net_amount
        string status
    }

    PAYMENT {
        bigint payment_id PK
        bigint bill_id FK
        decimal amount
        string payment_mode
        date payment_date
        string status
    }

    INSURANCE_CLAIM {
        bigint claim_id PK
        bigint patient_id FK
        bigint bill_id FK
        string insurance_company
        string policy_number
        decimal claim_amount
        date claim_date
        string status
    }

    LAB_TEST {
        bigint test_id PK
        bigint patient_id FK
        string test_name
        date test_date
        string status
    }

    LAB_REPORT {
        bigint report_id PK
        bigint test_id FK
        string result
        string verified_by
        date report_date
    }

    USERS {
        bigint user_id PK
        string username
        string password_hash
        string role
        string status
        datetime created_at
    }

    ROLES {
        bigint role_id PK
        string role_name
        string description
    }

    NOTIFICATION {
        bigint notification_id PK
        bigint user_id FK
        string type
        string message
        boolean is_read
        datetime created_at
    }

    AUDIT_LOG {
        bigint log_id PK
        bigint user_id FK
        string action
        string entity_name
        bigint entity_id
        string ip_address
        datetime action_time
    }
```

> **Note on microservice boundaries:** the FK arrows above describe the
> *logical* domain model shown in the source ER diagram. In the actual
> microservices implementation, each entity is owned by exactly one
> service's database, and cross-entity "relationships" that cross a
> service boundary (e.g. Appointment → Patient) are **not** JPA
> `@ManyToOne`/`@OneToMany` mappings — they are plain foreign-key `Long`
> columns, resolved via REST calls or Kafka events at runtime.
