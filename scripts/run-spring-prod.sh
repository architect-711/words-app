#!/usr/bin/env bash

echo "--> !!! Make sure you run all build commands before !!!"

# import tasty junk
source utils.sh
# populate with env vars
source_prod

docker compose up -d postgres_prod_db
docker compose up -d spring_backend