create table if not exists users
(
    id bigserial primary key,
    name varchar not null,
    email varchar not null unique,
    password varchar not null
);

create table if not exists tasks
(
    id bigserial primary key,
    title varchar not null,
    description varchar not null,
    status varchar not null,
    expiration_date timestamp not null
)