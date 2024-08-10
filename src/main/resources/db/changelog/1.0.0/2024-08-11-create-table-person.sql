--liquibase formatted sql

--changeset bzblz:1
create table person (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(256),
    project_id int NOT NULL REFERENCES project(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
--rollback drop table person;