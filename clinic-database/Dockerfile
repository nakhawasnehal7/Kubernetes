FROM postgres

COPY init.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_DB=clinic
ENV POSTGRES_USER=clinicuser
ENV POSTGRES_PASSWORD=clinicpass