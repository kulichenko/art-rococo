create extension if not exists "uuid-ossp";

create table if not exists "pictures"
(
    id          UUID unique         not null default uuid_generate_v1(),
    title       varchar(250) unique not null,
    description varchar(1000),
    content     bytea,
    museumId    uuid,
    artistId    uuid,
    primary key (id)
);

alter table "pictures"
    owner to postgres;