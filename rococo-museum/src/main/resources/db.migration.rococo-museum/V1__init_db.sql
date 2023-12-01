create extension if not exists "uuid-ossp";

create table if not exists "museum"
(
    id          UUID unique         not null default uuid_generate_v1(),
    title       varchar(250) unique not null,
    description varchar(2000),
    photo       bytea,
    city        varchar(50),
    country_id  uuid,
    primary key (id)
);

alter table "museum"
    owner to postgres;