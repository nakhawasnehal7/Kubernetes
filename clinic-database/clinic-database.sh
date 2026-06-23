#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 \
--username "$POSTGRES_USER" \
--dbname "$POSTGRES_DB" <<-EOSQL

CREATE USER clinicuser WITH PASSWORD 'clinicpass';

CREATE DATABASE clinic WITH OWNER clinicuser;

GRANT ALL PRIVILEGES ON DATABASE clinic TO clinicuser;

\c clinic

GRANT ALL ON SCHEMA public TO clinicuser;

EOSQL
