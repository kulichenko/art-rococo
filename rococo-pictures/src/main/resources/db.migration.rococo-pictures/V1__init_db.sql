create extension if not exists "uuid-ossp";

create table if not exists "pictures"
(
    id          UUID unique         not null default uuid_generate_v1(),
    title       varchar(250) unique not null,
    description varchar(2000),
    content     bytea,
    museum_id   uuid,
    artist_id   uuid,
    primary key (id)
);

alter table "pictures"
    owner to postgres;