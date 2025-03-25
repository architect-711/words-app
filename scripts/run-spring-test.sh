#!/usr/bin/env bash

cd backend || exit

docker compose up -d postgres_local_db
./gradlew test -i