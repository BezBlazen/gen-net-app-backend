--liquibase formatted sql

--changeset bzblz:1
create table project (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(256) NOT NULL,
    account_id bigint NOT NULL REFERENCES account(id) ON UPDATE CASCADE ON DELETE CASCADE
);
--rollback drop table project;