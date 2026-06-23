--
-- PostgreSQL database dump
--

\restrict P1WWZzt7vtxxa3o5LHrvXgO5Snibmf7mMZdliqn2ouzEIhD3dgJXgMhwLTTbPmh

-- Dumped from database version 18.2 (Debian 18.2-1.pgdg13+1)
-- Dumped by pg_dump version 18.2 (Debian 18.2-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: drugtreatment; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.drugtreatment (
    dosage real NOT NULL,
    enddate date,
    frequency integer NOT NULL,
    startdate date,
    id uuid NOT NULL,
    drug character varying(255)
);


ALTER TABLE public.drugtreatment OWNER TO clinicuser;

--
-- Name: patient; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.patient (
    dob date,
    id uuid NOT NULL,
    name character varying(255)
);


ALTER TABLE public.patient OWNER TO clinicuser;

--
-- Name: physiotherapytreatment; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.physiotherapytreatment (
    id uuid NOT NULL
);


ALTER TABLE public.physiotherapytreatment OWNER TO clinicuser;

--
-- Name: physiotherapytreatment_treatmentdates; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.physiotherapytreatment_treatmentdates (
    treatmentdates date,
    physiotherapytreatment_id uuid CONSTRAINT physiotherapytreatment_treat_physiotherapytreatment_id_not_null NOT NULL
);


ALTER TABLE public.physiotherapytreatment_treatmentdates OWNER TO clinicuser;

--
-- Name: provider; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.provider (
    id uuid NOT NULL,
    name character varying(255),
    npi character varying(255)
);


ALTER TABLE public.provider OWNER TO clinicuser;

--
-- Name: radiologytreatment; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.radiologytreatment (
    id uuid NOT NULL,
    treatmentdates date[]
);


ALTER TABLE public.radiologytreatment OWNER TO clinicuser;

--
-- Name: surgerytreatment; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.surgerytreatment (
    surgerydate date,
    id uuid NOT NULL,
    dischargeinstructions character varying(255)
);


ALTER TABLE public.surgerytreatment OWNER TO clinicuser;

--
-- Name: treatment; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.treatment (
    id uuid NOT NULL,
    patient_id uuid,
    provider_id uuid,
    diagnosis character varying(255)
);


ALTER TABLE public.treatment OWNER TO clinicuser;

--
-- Name: treatment_treatment; Type: TABLE; Schema: public; Owner: clinicuser
--

CREATE TABLE public.treatment_treatment (
    treatment_id uuid NOT NULL,
    followuptreatments_id uuid NOT NULL
);


ALTER TABLE public.treatment_treatment OWNER TO clinicuser;

--
-- Name: drugtreatment drugtreatment_pkey; Type: CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.drugtreatment
    ADD CONSTRAINT drugtreatment_pkey PRIMARY KEY (id);


--
-- Name: patient patient_pkey; Type: CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (id);


--
-- Name: physiotherapytreatment physiotherapytreatment_pkey; Type: CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.physiotherapytreatment
    ADD CONSTRAINT physiotherapytreatment_pkey PRIMARY KEY (id);


--
-- Name: provider provider_pkey; Type: CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.provider
    ADD CONSTRAINT provider_pkey PRIMARY KEY (id);


--
-- Name: radiologytreatment radiologytreatment_pkey; Type: CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.radiologytreatment
    ADD CONSTRAINT radiologytreatment_pkey PRIMARY KEY (id);


--
-- Name: surgerytreatment surgerytreatment_pkey; Type: CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.surgerytreatment
    ADD CONSTRAINT surgerytreatment_pkey PRIMARY KEY (id);


--
-- Name: treatment treatment_pkey; Type: CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.treatment
    ADD CONSTRAINT treatment_pkey PRIMARY KEY (id);


--
-- Name: treatment_treatment treatment_treatment_followuptreatments_id_key; Type: CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.treatment_treatment
    ADD CONSTRAINT treatment_treatment_followuptreatments_id_key UNIQUE (followuptreatments_id);


--
-- Name: treatment fk1ufrpnlshhbkpdop9j0rf478b; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.treatment
    ADD CONSTRAINT fk1ufrpnlshhbkpdop9j0rf478b FOREIGN KEY (provider_id) REFERENCES public.provider(id);


--
-- Name: treatment_treatment fk3mpvn856171n0nameg9n6bx22; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.treatment_treatment
    ADD CONSTRAINT fk3mpvn856171n0nameg9n6bx22 FOREIGN KEY (treatment_id) REFERENCES public.treatment(id);


--
-- Name: physiotherapytreatment fk6br4fijpn9sm57tbm4r2eojl9; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.physiotherapytreatment
    ADD CONSTRAINT fk6br4fijpn9sm57tbm4r2eojl9 FOREIGN KEY (id) REFERENCES public.treatment(id);


--
-- Name: treatment_treatment fk6xg7gp37m9219fb96dvfosa8r; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.treatment_treatment
    ADD CONSTRAINT fk6xg7gp37m9219fb96dvfosa8r FOREIGN KEY (followuptreatments_id) REFERENCES public.treatment(id);


--
-- Name: radiologytreatment fkg7ekm1da80vjhk2jaajrsm6u6; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.radiologytreatment
    ADD CONSTRAINT fkg7ekm1da80vjhk2jaajrsm6u6 FOREIGN KEY (id) REFERENCES public.treatment(id);


--
-- Name: treatment fkksawx3hel8rl2rttwpx9o6thf; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.treatment
    ADD CONSTRAINT fkksawx3hel8rl2rttwpx9o6thf FOREIGN KEY (patient_id) REFERENCES public.patient(id);


--
-- Name: surgerytreatment fkqju7mrkwgv518qk71rt85d05i; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.surgerytreatment
    ADD CONSTRAINT fkqju7mrkwgv518qk71rt85d05i FOREIGN KEY (id) REFERENCES public.treatment(id);


--
-- Name: drugtreatment fktcfusowqplg7i8jc9mir6re8q; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.drugtreatment
    ADD CONSTRAINT fktcfusowqplg7i8jc9mir6re8q FOREIGN KEY (id) REFERENCES public.treatment(id);


--
-- Name: physiotherapytreatment_treatmentdates fkun0d8klpd1fkyata1p1fghos; Type: FK CONSTRAINT; Schema: public; Owner: clinicuser
--

ALTER TABLE ONLY public.physiotherapytreatment_treatmentdates
    ADD CONSTRAINT fkun0d8klpd1fkyata1p1fghos FOREIGN KEY (physiotherapytreatment_id) REFERENCES public.physiotherapytreatment(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO clinicuser;


--
-- PostgreSQL database dump complete
--

\unrestrict P1WWZzt7vtxxa3o5LHrvXgO5Snibmf7mMZdliqn2ouzEIhD3dgJXgMhwLTTbPmh

