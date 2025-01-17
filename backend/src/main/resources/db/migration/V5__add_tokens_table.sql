create table tokens (
    id bigint generated by default as identity primary key,
    access_token varchar not null,
    refresh_token varchar not null,
    is_logged_out boolean not null,
    person_id bigint references person(id) not null
);