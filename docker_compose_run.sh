#!/bin/bash

if [[ $1 = "build" ]]; then
  cd "backend" || exit
  ./gradlew clean build
  cd ".."
fi

cp .env backend/.env

source .env

if ! docker volume ls | grep -q "$POSTGRES_VOLUME_NAME"; then
  docker volume create "$POSTGRES_VOLUME_NAME"
fi

docker compose build

rm backend/.env

docker compose up -d
