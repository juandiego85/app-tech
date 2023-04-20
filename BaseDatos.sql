-- Database: techappbd

-- DROP DATABASE techappbd;

CREATE DATABASE techappbd
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	






-- SEQUENCE: public.persona_persona_id_seq

-- DROP SEQUENCE public.persona_persona_id_seq;

CREATE SEQUENCE public.persona_persona_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.persona_persona_id_seq
    OWNER TO postgres;

-- Table: public.persona

-- DROP TABLE public.persona;

CREATE TABLE public.persona
(
    dtype character varying(31) COLLATE pg_catalog."default" NOT NULL,
    persona_id bigint NOT NULL DEFAULT nextval('persona_persona_id_seq'::regclass),
    direccion character varying(255) COLLATE pg_catalog."default" NOT NULL,
    edad integer NOT NULL,
    genero character varying(255) COLLATE pg_catalog."default" NOT NULL,
    identificacion character varying(255) COLLATE pg_catalog."default" NOT NULL,
    nombre character varying(255) COLLATE pg_catalog."default" NOT NULL,
    telefono character varying(255) COLLATE pg_catalog."default" NOT NULL,
    cliente_id bigint NOT NULL,
    contrasena character varying(255) COLLATE pg_catalog."default" NOT NULL,
    estado boolean NOT NULL,
    CONSTRAINT persona_pkey PRIMARY KEY (persona_id),
    CONSTRAINT uk_r5vsms84ih2viwd6tatk9o5pq UNIQUE (identificacion),
    CONSTRAINT uk_sk2ttlpp6eid09hulymqx6cc5 UNIQUE (cliente_id)
)TABLESPACE pg_default;

ALTER TABLE public.persona
    OWNER to postgres;
	



-- SEQUENCE: public.cuenta_cuenta_id_seq

-- DROP SEQUENCE public.cuenta_cuenta_id_seq;

CREATE SEQUENCE public.cuenta_cuenta_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.cuenta_cuenta_id_seq
    OWNER TO postgres;
	
-- Table: public.cuenta

-- DROP TABLE public.cuenta;

CREATE TABLE public.cuenta
(
    cuenta_id bigint NOT NULL DEFAULT nextval('cuenta_cuenta_id_seq'::regclass),
    estado boolean NOT NULL,
    numero_cuenta character varying(255) COLLATE pg_catalog."default" NOT NULL,
    saldo_inicial double precision NOT NULL,
    tipo_cuenta character varying(255) COLLATE pg_catalog."default" NOT NULL,
    cliente_id bigint NOT NULL,
    CONSTRAINT cuenta_pkey PRIMARY KEY (cuenta_id),
    CONSTRAINT uk_pj7ncg765kt4klndu25bwbwe4 UNIQUE (numero_cuenta),
    CONSTRAINT fklxjuylbfwqesghtnemd5dbldj FOREIGN KEY (cliente_id)
        REFERENCES public.persona (persona_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)TABLESPACE pg_default;

ALTER TABLE public.cuenta
    OWNER to postgres;
	

-- SEQUENCE: public.movimiento_movimiento_id_seq

-- DROP SEQUENCE public.movimiento_movimiento_id_seq;

CREATE SEQUENCE public.movimiento_movimiento_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.movimiento_movimiento_id_seq
    OWNER TO postgres;

-- Table: public.movimiento

-- DROP TABLE public.movimiento;

CREATE TABLE public.movimiento
(
    movimiento_id bigint NOT NULL DEFAULT nextval('movimiento_movimiento_id_seq'::regclass),
    fecha timestamp(6) without time zone NOT NULL,
    saldo double precision NOT NULL,
    tipo_movimiento character varying(255) COLLATE pg_catalog."default" NOT NULL,
    valor double precision NOT NULL,
    cuenta_id bigint NOT NULL,
    CONSTRAINT movimiento_pkey PRIMARY KEY (movimiento_id),
    CONSTRAINT fk4ea11fe7p3xa1kwwmdgi9f2fi FOREIGN KEY (cuenta_id)
        REFERENCES public.cuenta (cuenta_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)TABLESPACE pg_default;

ALTER TABLE public.movimiento
    OWNER to postgres;