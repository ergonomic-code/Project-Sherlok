DROP SCHEMA IF EXISTS public CASCADE;

CREATE SCHEMA public;

CREATE TABLE producer
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR                  NOT NULL,
    address    VARCHAR                  NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE product
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR                  NOT NULL,
    description TEXT                     NOT NULL,
    producer    BIGINT                   NOT NULL REFERENCES producer,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL
);