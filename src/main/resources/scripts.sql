create table person (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(256)
);

create table users (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    -- name varchar(256) NOT NULL,
    username varchar(20) NOT NULL,
    password varchar(64) NOT NULL,
    person_id int REFERENCES person(id) ON UPDATE CASCADE ON DELETE SET NULL,
    user_role varchar(10) NOT NULL
);

create table source (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(256) NOT NULL
);

create table file (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    path varchar(256) NOT NULL,
    title varchar(256) NOT NULL,
    project_id int NOT NULL REFERENCES project(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

create table project (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(256) NOT NULL,
    user_id int NOT NULL REFERENCES users(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
alter table person add column project_id int NOT NULL REFERENCES project(id) ON UPDATE CASCADE ON DELETE RESTRICT;
alter table source add column project_id int NOT NULL REFERENCES project(id) ON UPDATE CASCADE ON DELETE RESTRICT;
-- alter table file add column project_id int NOT NULL REFERENCES project(id) ON UPDATE CASCADE ON DELETE RESTRICT;

create table link_multimedia_record_ (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(256) NOT NULL
);


