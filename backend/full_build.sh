#! /bin/bash

: '
    This file is required to automate the building app, container and run it process;

    run: ./this_file_name.sh [ARGUMENTS]

    Available arguments:
    docker_build (first) to make build for docker
    run_container_after_build (second) .
'

function cleanup() {
    rm .env
}

function build_bundles() {
    readonly BUILD_NAME="$APP_NAME"-"$APP_VERSION".jar

    ./gradlew clean build

    docker build --build-arg BUILD_NAME="$BUILD_NAME" -t architect711/words-app-spring-backend .

}

function source_env() {
    cp ../.env .env

    source .env
}

function replace_postgres_host_value() {
    local env_file="$1"
    local key="$2"
    local value="$3"

    sed -i "s/^${key}=.*/${key}=${value}/g" "$env_file"
}

function check_env_file_existence() {
    if [ ! -f "$1" ]; then
        echo "File $1 not found"
        exit 1
    fi
}

function build() {
    readonly ENV_FILE="../.env"
    readonly KEY="POSTGRES_DEV_HOST"

    check_env_file_existence $ENV_FILE

    # change jdbc postgres host to localhost
    if [ "$1" = "docker_build" ]; then
        replace_postgres_host_value "$ENV_FILE" "$KEY" "host.docker.internal"
    fi

    source_env
    build_bundles "$3"
    replace_postgres_host_value "$ENV_FILE" "$KEY" "localhost"
    cleanup

    if [ "$2" = "run_container_after_build" ]; then
        docker run "$DOCKER_REGISTRY"/"$SPRING_IMAGE_NAME"
    fi
}
build "$@"

