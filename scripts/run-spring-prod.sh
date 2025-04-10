#!/usr/bin/env bash

echo "Make sure you run all build commands before!"

source utils.sh
source_prod

docker compose up -d postgres_prod_db
docker compose up -d spring_backend