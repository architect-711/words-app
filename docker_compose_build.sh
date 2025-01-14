#!/usr/bin/zsh

# Import all from .env file
source .env

# Up local db for tests
docker compose up -d postgres_local_db

# Build Spring app
cd "backend" && ./gradlew clean build -PBACKEND_VERSION="${BACKEND_VERSION}" && cd ".."

# Build docker containers
docker compose build spring_backend