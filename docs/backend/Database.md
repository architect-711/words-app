# Database

## Description

In this file the database scheme is explained. Although the server will automatically
create it, you may configure the database manually.

## Tables

1. Users
2. Words
3. Authorities

### Users

Contains only user's primary info.

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(16) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL
);
```
---

### Authorities

Contains user credentials

Scheme:
```sql
CREATE TYPE role AS ENUM ('admin', 'user');
CREATE TYPE user_authorities AS ENUM ('read', 'update', 'delete');

CREATE TABLE authorities (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    api_key VARCHAR(128) NOT NULL, 
    authorities user_authorities[],
    role role,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);
```
---
### Words

Table contains all saved word info

Scheme:
```sql
CREATE TYPE language AS ENUM ('english');

CREATE TABLE words (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(45) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    language language,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);
```