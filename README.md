# Hospital Management System (HMS)

A microservices-based Hospital Management System built with Java 21, Spring Boot 3,
PostgreSQL, JWT security, Kafka, Spring Cloud Gateway, Docker, Kubernetes and GitHub Actions.

## ⚠️ Current status — read this first

This repository is being delivered in phases. **Phase 1 (this drop) is fully implemented,
reviewed, and structured for real use:**

| Service | Status |
|---|---|
| `api-gateway` | ✅ Implemented |
| `auth-service` | ✅ Implemented (JWT, BCrypt, role-based auth) |
| `patient-service` | ✅ Implemented (full CRUD, tests) |
| `doctor-service` | ✅ Implemented (full CRUD) |
| `department-service` | ✅ Implemented (full CRUD) |
| `appointment-service` | ✅ Implemented (full CRUD + Kafka producer) |
| `prescription-service`, `admission-service`, `bed-service`, `medicine-service`, `billing-service`, `payment-service`, `lab-service`, `insurance-service`, `notification-service`, `audit-service` | 🔲 Scaffolded only (pom.xml + application class + config) — business logic to follow in later phases, using the same layered pattern as the services above |

**Important:** this project was written in an environment without access to Maven Central,
Docker, Kubernetes, or a running PostgreSQL/Kafka instance, so **it has not been
build-tested or run**. Before relying on it:

```bash
mvn -B clean package
```

and fix any compile errors that surface — I've checked package names, imports, and
method signatures by hand, but a real `mvn` run is the only reliable verification.

---

## 1. Requirements

- Java 21 (Temurin recommended)
- Maven 3.9+
- Docker & Docker Compose
- PostgreSQL 16 (or use the bundled docker-compose service)
- Apache Kafka (or use the bundled docker-compose service)
- kubectl + a Kubernetes cluster (for k8s deployment, optional for local dev)

## 2. Install Java 21

```bash
sdk install java 21-tem     # via SDKMAN, or use your OS package manager
java -version
```

## 3. Install Maven

```bash
sdk install maven
mvn -version
```

## 4. PostgreSQL setup (local, without Docker)

```bash
# create one database per service, e.g.:
createdb auth_service
createdb patient_service
createdb doctor_service
createdb department_service
createdb appointment_service
# ...repeat for every service once implemented
```

Or skip this and use `docker-compose up postgres`, which auto-creates all databases
via `infra/postgres/init-databases.sh`.

## 5. Kafka setup

Use the bundled `docker-compose` Kafka + Zookeeper containers (recommended for local dev),
or point `KAFKA_BOOTSTRAP_SERVERS` at an existing cluster.

## 6. Environment variables

Copy `.env.example` to `.env` and adjust as needed:

```bash
cp .env.example .env
```

Key variables:

| Variable | Purpose |
|---|---|
| `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` | Per-service datasource config |
| `JWT_SECRET` | Base64 HMAC-SHA256 signing key — **override in every non-local environment** |
| `JWT_EXPIRATION_MS` | Token lifetime in milliseconds |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address |

## 7. Build all services

```bash
mvn -B clean package
```

Build a single service (and its dependency modules):

```bash
mvn -pl patient-service -am clean package
```

## 8. Run an individual service locally

```bash
cd auth-service
mvn spring-boot:run
```

Repeat per service, or run the jar directly:

```bash
java -jar auth-service/target/auth-service-1.0.0.jar
```

## 9. Run everything with Docker Compose

```bash
docker compose up --build
```

This starts: PostgreSQL, Kafka/Zookeeper, the API Gateway, and the five Phase-1
services. Add a new block to `docker-compose.yml` (same pattern as the existing
services) as you implement the remaining ones.

## 10. Ports

| Service | Port |
|---|---|
| api-gateway | 8888 |
| auth-service | 8080 |
| patient-service | 8081 |
| doctor-service | 8082 |
| department-service | 8083 |
| appointment-service | 8084 |
| prescription-service | 8085 |
| admission-service | 8086 |
| bed-service | 8087 |
| medicine-service | 8088 |
| billing-service | 8089 |
| payment-service | 8090 |
| lab-service | 8091 |
| insurance-service | 8092 |
| notification-service | 8093 |
| audit-service | 8094 |

## 11. API Gateway

All external traffic should go through `http://localhost:8888`, which routes by path
prefix to the correct service (see `api-gateway/src/main/resources/application.yml`).
Hitting a service's own port directly is fine for local debugging but bypasses the gateway.

## 12. Authentication & JWT

1. `POST /api/auth/register` — create a user with a role.
2. `POST /api/auth/login` — returns a JWT `accessToken`.
3. Pass it on every subsequent request: `Authorization: Bearer <accessToken>`.

Passwords are hashed with BCrypt and never stored or returned in plain text
(`UserResponse` intentionally omits `passwordHash`).

## 13. Kafka event flow

`appointment-service` publishes `AppointmentCreatedEvent` to the
`hospital.appointment.events` topic whenever a new appointment is booked
(see `AppointmentEventProducer`). This is fire-and-forget — booking an
appointment does not wait on any downstream consumer. Planned consumers
(`notification-service`, `audit-service`) will be added in Phase 2.

See `docs/architecture-diagram.md` for the full intended event map across
every service.

## 14. Kubernetes

```bash
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-secrets.yaml      # edit real values first!
kubectl apply -f k8s/02-configmap.yaml
kubectl apply -f k8s/
```

Manifests exist for the Phase-1 services only; copy the pattern in
`k8s/patient-service.yaml` for each new service as it's implemented.

## 15. CI/CD

`.github/workflows/ci.yml` runs on every push/PR to `main`:

1. Checkout → Java 21 setup → `mvn clean package` → `mvn test`
2. On `main` only: builds and pushes a Docker image per Phase-1 service (needs
   `DOCKERHUB_USERNAME` / `DOCKERHUB_TOKEN` secrets)
3. Deploys to Kubernetes via `kubectl apply -f k8s/` (needs a `KUBE_CONFIG` secret)

No credentials are hard-coded; everything sensitive comes from GitHub Secrets.

## 16. Testing

```bash
mvn test                       # all modules
mvn -pl auth-service test       # single module
```

Implemented so far: `PatientServiceTest`, `JwtServiceTest`, `AuthServiceTest`
(unit tests with JUnit 5 + Mockito). Controller-level `@WebMvcTest` and
repository tests are a good next addition per service.

## 17. Project structure

```
hospital-management-system/
├── api-gateway/
├── auth-service/
├── patient-service/
├── doctor-service/
├── department-service/
├── appointment-service/
├── prescription-service/        (scaffold)
├── admission-service/           (scaffold)
├── bed-service/                 (scaffold)
├── medicine-service/            (scaffold)
├── billing-service/             (scaffold)
├── payment-service/             (scaffold)
├── lab-service/                 (scaffold)
├── insurance-service/           (scaffold)
├── notification-service/        (scaffold)
├── audit-service/               (scaffold)
├── infra/postgres/init-databases.sh
├── docker-compose.yml
├── k8s/
├── docs/                        (ER + architecture Mermaid diagrams)
├── .github/workflows/ci.yml
└── README.md
```

Each implemented service follows:

```
src/main/java/com/hms/<service>/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
├── config/
└── exception/
```

## 18. Sample API requests

**Register**
```bash
curl -X POST http://localhost:8888/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin1","password":"SecurePass123","role":"ADMIN"}'
```

**Login**
```bash
curl -X POST http://localhost:8888/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin1","password":"SecurePass123"}'
# => { "accessToken": "...", "tokenType": "Bearer", "expiresIn": 3600 }
```

**Create Patient**
```bash
curl -X POST http://localhost:8888/api/patients \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"name":"Rahul Verma","gender":"MALE","dob":"1995-04-12","phone":"9876543210","email":"rahul@example.com","bloodGroup":"B+"}'
```

**Create Doctor**
```bash
curl -X POST http://localhost:8888/api/doctors \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"departmentId":1,"name":"Dr. Anita Rao","specialization":"Cardiology","qualification":"MD","experience":10,"phone":"9123456789","email":"anita@example.com","consultationFee":800,"status":"ACTIVE"}'
```

**Create Appointment**
```bash
curl -X POST http://localhost:8888/api/appointments \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"patientId":1,"doctorId":1,"appointmentDate":"2026-10-05","appointmentTime":"10:30:00","reason":"Routine checkup","status":"SCHEDULED"}'
```

**Generate Bill / Payment / Lab Test** — endpoints for `billing-service`,
`payment-service`, and `lab-service` will follow the same `POST /api/billing`,
`POST /api/payments`, `POST /api/labs` shape once those services are implemented
in Phase 2; see the scaffolded `application.yml` in each for the reserved port.

## 19. Swagger / OpenAPI

Each implemented service exposes Swagger UI at:

```
http://localhost:<service-port>/swagger-ui.html
```

e.g. `http://localhost:8081/swagger-ui.html` for patient-service.

## 20. Security notes

- Passwords: BCrypt-hashed, never returned in API responses.
- JWT: HMAC-SHA256, base64 secret via `JWT_SECRET` env var — the default in
  `application.yml` is a **development-only placeholder**, rotate it for anything
  beyond local testing.
- No production secrets are committed; `.env` is gitignored, k8s secrets are
  templates with placeholder values.
