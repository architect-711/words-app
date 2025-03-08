#!/usr/bin/env bash

export $(grep -v '^#' .env | xargs)

cd backend || exit

docker compose up -d postgres_local_db && ./gradlew bootRun --args='--spring.profiles.active=dev,postgres'