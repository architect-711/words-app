#!/usr/bin/env bash

cd backend || exit

./gradlew clean

docker compose up -d postgres_local_db && ./gradlew bootRun --args='--spring.profiles.active=dev,postgres' 