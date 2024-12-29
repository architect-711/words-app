#!/usr/bin/zsh

source ./build_utils.sh

function prepare_docker() {
    # clean everything
    docker compose down
    docker volume rm words-app_local_db_volume words-app_prod_db_volume 2> /dev/null || true
}

function build_spring() {
    # force substitution of env vars which contain another env vars. This won't conflict, since it overrides (!!!)
    # Otherwise Dockerfile gets unsubstituted ${BACKEND_BUILD_NAME} from .env
    source .env
    cd "backend" && ./gradlew clean build -PBACKEND_VERSION="${BACKEND_VERSION}" && cd ".."
}

if ! check_env_files . ; then
    exit 1
fi

import_all_env_vars .env .env.*
build_spring
prepare_docker && docker compose up -d postgres_local_db
if [ $? -eq 0 ] ; then
    docker compose build spring_backend
else
    echo "Failed to build app"
fi
