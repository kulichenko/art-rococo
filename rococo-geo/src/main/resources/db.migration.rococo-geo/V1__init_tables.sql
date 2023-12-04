create extension if not exists "uuid-ossp";

create table if not exists geo
(
    id   UUID unique        not null default uuid_generate_v1() primary key,
    name varchar(50) unique not null
);

alter table geo
    owner to postgres;
