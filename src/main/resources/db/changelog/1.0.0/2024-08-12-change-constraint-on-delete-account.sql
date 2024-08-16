--liquibase formatted sql

--changeset bzblz:1
alter table project drop constraint project_account_id_fkey, add constraint project_account_id_fkey foreign key (account_id) references account(id) on delete cascade on update cascade;
alter table person drop constraint person_project_id_fkey, add constraint person_project_id_fkey foreign key (project_id) references project(id) on delete cascade on update cascade;
--rollback alter table project drop constraint project_account_id_fkey, add constraint project_account_id_fkey foreign key (account_id) references account(id) on delete restrict on update cascade;
--rollback alter table person drop constraint person_project_id_fkey, add constraint person_project_id_fkey foreign key (project_id) references project(id) on delete restrict on update cascade;