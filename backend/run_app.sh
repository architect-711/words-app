#!/usr/bin/zsh

# import utils, required for this process
source ../build_utils.sh

# import all env vars
import_all_env_vars ../.env ../.env.*

# Run app only if db has been successfully started
docker compose up -d postgres_local_db && ./gradlew bootRun