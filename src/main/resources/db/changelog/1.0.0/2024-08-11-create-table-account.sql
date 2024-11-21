--liquibase formatted sql

--changeset bzblz:1
create table account (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username varchar(64) NOT NULL,
    password varchar(64) NOT NULL,
    email varchar(64),
    created_at timestamp,
    role varchar(20) NOT NULL
);
--rollback drop table account;