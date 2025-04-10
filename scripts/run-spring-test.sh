#!/usr/bin/env bash

echo "!!! Use IDEs testing possibilities for better output. But note, that you'll need to import particular env vars"

source utils.sh
source_dev

cd ../backend || exit

docker compose up -d postgres_local_db
./gradlew test -i