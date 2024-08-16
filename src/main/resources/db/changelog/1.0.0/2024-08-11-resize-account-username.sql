--liquibase formatted sql

--changeset bzblz:1
--comment session account username = _ + sessionId
alter table account alter column username type varchar(33);
--rollback alter table account alter column username type varchar(32);