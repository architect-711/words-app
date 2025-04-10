#!/usr/bin/zsh

source utils.sh
source_dev

# Up local db for tests
docker compose up -d postgres_local_db

# Build Spring app
cd ../backend && ./gradlew clean test build && cd ..

if [ "$?" -ne 0 ]; then
  echo "App building has been successfully FAILED."
  exit 1;
fi

cd scripts || exit
# it will replace all came from dev
source_prod
# Build docker container
docker compose build spring_backend
# Stop local db
docker compose stop postgres_local_db