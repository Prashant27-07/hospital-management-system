# System Architecture — Hospital Management System

```mermaid
flowchart TB
    User[User / Client App]
    LB[Kubernetes Ingress / Load Balancer]
    GW[API Gateway<br/>Spring Cloud Gateway]

    User --> LB --> GW

    subgraph Services[Microservices]
        AUTH[auth-service]
        PAT[patient-service]
        DOC[doctor-service]
        DEPT[department-service]
        APPT[appointment-service]
        PRES[prescription-service]
        ADM[admission-service]
        BED[bed-service]
        MED[medicine-service]
        BILL[billing-service]
        PAY[payment-service]
        LAB[lab-service]
        INS[insurance-service]
    end

    GW --> AUTH
    GW --> PAT
    GW --> DOC
    GW --> DEPT
    GW --> APPT
    GW --> PRES
    GW --> ADM
    GW --> BED
    GW --> MED
    GW --> BILL
    GW --> PAY
    GW --> LAB
    GW --> INS

    subgraph DBs[Databases — one schema per service]
        AUTHDB[(auth_service)]
        PATDB[(patient_service)]
        DOCDB[(doctor_service)]
        DEPTDB[(department_service)]
        APPTDB[(appointment_service)]
        OTHERDB[(...one DB per remaining service)]
    end

    AUTH --> AUTHDB
    PAT --> PATDB
    DOC --> DOCDB
    DEPT --> DEPTDB
    APPT --> APPTDB
    PRES --> OTHERDB
    ADM --> OTHERDB
    BED --> OTHERDB
    MED --> OTHERDB
    BILL --> OTHERDB
    PAY --> OTHERDB
    LAB --> OTHERDB
    INS --> OTHERDB

    APPT -- publishes --> KAFKA{{Kafka}}
    PRES -- publishes --> KAFKA
    ADM -- publishes --> KAFKA
    BILL -- publishes --> KAFKA
    PAY -- publishes --> KAFKA
    LAB -- publishes --> KAFKA
    INS -- publishes --> KAFKA

    KAFKA -- consumes --> NOTIF[notification-service]
    KAFKA -- consumes --> AUDIT[audit-service]

    subgraph CICD[CI/CD Pipeline]
        GH[GitHub Repo]
        GHA[GitHub Actions]
        DOCKER[Docker Build]
        K8S[Kubernetes Deploy]
    end

    GH --> GHA --> DOCKER --> K8S
    K8S -.deploys.-> Services
```

## Notes

- Every business microservice owns its own PostgreSQL schema; there is no shared database.
- Cross-service reads happen over synchronous REST calls (e.g. billing-service calling patient-service to display a patient's name on an invoice).
- Cross-service side effects (notifications, audit trail) happen asynchronously over Kafka so that, for example, appointment-service never blocks on notification-service being available.
- The API Gateway is the single entry point; individual services are not exposed directly outside the cluster.
