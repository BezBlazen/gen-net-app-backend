--liquibase formatted sql

--changeset bzblz:1
create table project (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(256) NOT NULL,
    account_id int NOT NULL REFERENCES account(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
--rollback drop table project;