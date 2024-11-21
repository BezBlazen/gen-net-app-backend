--liquibase formatted sql

--changeset bzblz:1
create table person (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(256),
    project_id bigint NOT NULL REFERENCES project(id) ON UPDATE CASCADE ON DELETE CASCADE
);
--rollback drop table person;