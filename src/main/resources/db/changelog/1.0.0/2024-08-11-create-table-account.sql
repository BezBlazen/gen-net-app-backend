--liquibase formatted sql

--changeset bzblz:1
create table account (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username varchar(32) NOT NULL,
    password varchar(64) NOT NULL,
    account_role varchar(20) NOT NULL
);
--rollback drop table account;