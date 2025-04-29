#!/usr/bin/env bash

source utils.sh

# populate with dev env vars
source_dev
# go to backend folder
cd "$BACKEND_FOLDER_REL" || die "Couldn't find backend folder, specified -->$BACKEND_FOLDER_REL<--"

docker compose up -d postgres_local_db

if [ "$1" = "container" ]; then
  ./gradlew clean build -x test

  # overwrite JDBC url, since working in container
  export DATABASE_URL="${DATABASE_URL/localhost:5434/"postgres_local_db:5432"}"

  docker compose up --build --remove-orphans -d spring_backend
else
  ./gradlew bootRun --args='--spring.profiles.active=dev,postgres'
fi
