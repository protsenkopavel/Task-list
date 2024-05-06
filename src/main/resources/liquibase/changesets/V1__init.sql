create table if not exists users
(
    id bigserial primary key,
    name varchar not null,
    password varchar not null
);

create table if not exists tasks
(
    id bigserial primary key,
    title varchar not null,
    description varchar not null
)