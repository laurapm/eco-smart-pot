-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: new_database | type: DATABASE --
-- -- DROP DATABASE IF EXISTS new_database;
-- CREATE DATABASE new_database;
-- -- ddl-end --
-- 

--CREATE ROLE GARDENER 
--WITH NOSUPERUSER,
--	 NOCREATEDB,
--	 NOCREATEROLE,
--	 INHERIT,
--	 NOREPLICATION,
--	 NOBYPASSRLS,
--	 PASSWORD NULL
--;

CREATE USER gardener
WITH PASSWORD 'test';

-- object: public."TREATMENTS" | type: TABLE --
-- DROP TABLE IF EXISTS public."TREATMENTS" CASCADE;
CREATE TABLE public."TREATMENTS"(
	"ID" int8 NOT NULL,
	"TREATMENT" json,
	CONSTRAINT "TREATMENTS_pk" PRIMARY KEY ("ID")

);
-- ddl-end --
ALTER TABLE public."TREATMENTS" OWNER TO postgres;
-- ddl-end --

-- object: public."TREAMENT_HISTORY" | type: TABLE --
-- DROP TABLE IF EXISTS public."TREAMENT_HISTORY" CASCADE;
CREATE TABLE public."TREAMENT_HISTORY"(
	"ID" int8 NOT NULL,
	"ID_TREATMENTS" int8,
	"DATE" timestamp,
	CONSTRAINT "TREAMENT_HISTORY_pk" PRIMARY KEY ("ID")

);
-- ddl-end --
ALTER TABLE public."TREAMENT_HISTORY" OWNER TO postgres;
-- ddl-end --

-- object: "TREATMENTS_fk" | type: CONSTRAINT --
-- ALTER TABLE public."TREAMENT_HISTORY" DROP CONSTRAINT IF EXISTS "TREATMENTS_fk" CASCADE;
ALTER TABLE public."TREAMENT_HISTORY" ADD CONSTRAINT "TREATMENTS_fk" FOREIGN KEY ("ID_TREATMENTS")
REFERENCES public."TREATMENTS" ("ID") MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public."TEMP_EXT" | type: TABLE --
-- DROP TABLE IF EXISTS public."TEMP_EXT" CASCADE;
CREATE TABLE public."TEMP_EXT"(
	"ID" int8 NOT NULL,
	"TEMPERATURE" float8,
	"MEASURE_TIME" timestamp,
	CONSTRAINT "TEMP_EXT_pk" PRIMARY KEY ("ID")

);
-- ddl-end --
ALTER TABLE public."TEMP_EXT" OWNER TO postgres;
-- ddl-end --

-- object: public."POT_INT" | type: TABLE --
-- DROP TABLE IF EXISTS public."POT_INT" CASCADE;
CREATE TABLE public."POT_INT"(
	"ID" int8 NOT NULL,
	"TEMPERATURE" float8,
	"HUMIDITY" float8,
	"MEASURE_TIME" timestamp,
	CONSTRAINT "POT_INT_pk" PRIMARY KEY ("ID")

);
-- ddl-end --
ALTER TABLE public."POT_INT" OWNER TO postgres;
-- ddl-end --

-- object: public."LUM_EXT" | type: TABLE --
-- DROP TABLE IF EXISTS public."LUM_EXT" CASCADE;
CREATE TABLE public."LUM_EXT"(
	"ID" int8 NOT NULL,
	"LUMINOSITY" float8,
	"TIME_MEASURE" timestamp,
	CONSTRAINT "LUM_EXT_pk" PRIMARY KEY ("ID")

);
-- ddl-end --
ALTER TABLE public."LUM_EXT" OWNER TO postgres;
-- ddl-end --

-- object: public."HUM_EXT" | type: TABLE --
-- DROP TABLE IF EXISTS public."HUM_EXT" CASCADE;
CREATE TABLE public."HUM_EXT"(
	"ID" int8 NOT NULL,
	"HUMIDITY" timestamp,
	CONSTRAINT "HUM_EXT_pk" PRIMARY KEY ("ID")

);
-- ddl-end --
ALTER TABLE public."HUM_EXT" OWNER TO postgres;
-- ddl-end --


