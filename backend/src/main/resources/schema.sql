-- Drop everything
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS users;
DROP TYPE IF EXISTS user_authorities;
DROP TYPE IF EXISTS user_role;
DROP TYPE IF EXISTS language;


-- Create types
CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');
CREATE TYPE user_authorities AS ENUM ('READ', 'UPDATE', 'DELETE');
CREATE TYPE language AS ENUM ('ENGLISH');


-- Create tables
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(16) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL
);

CREATE TABLE authorities (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    api_key VARCHAR(128) NOT NULL UNIQUE, 
    authorities user_authorities[],
    role user_role,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE words (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(45) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    language language,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);
