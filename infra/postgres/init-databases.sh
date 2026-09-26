#!/bin/bash
set -e

# Creates one database per microservice on container first-boot.
# Postgres runs this automatically because it's mounted into
# /docker-entrypoint-initdb.d/ (see docker-compose.yml).

DATABASES=(
  auth_service
  patient_service
  doctor_service
  department_service
  appointment_service
  prescription_service
  admission_service
  bed_service
  medicine_service
  billing_service
  payment_service
  lab_service
  insurance_service
  notification_service
  audit_service
)

for db in "${DATABASES[@]}"; do
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    SELECT 'CREATE DATABASE $db'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$db')\gexec
EOSQL
done
