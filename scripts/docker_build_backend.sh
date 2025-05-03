#!/usr/bin/zsh

source utils.sh

# populate with prod env vars
source_prod

IMAGE_TITLE=$DOCKER_REGISTRY/$BACKEND_IMAGE_NAME

# Build docker container
docker compose build spring_backend
# fucking Docker sometimes produces dangling images
docker image prune -f

# shellcheck disable=SC2086
docker tag "$IMAGE_TITLE":$BACKEND_VERSION $IMAGE_TITLE:latest

echo "--> Built the image, the new ones (should be 2):"
docker image ls -f "reference=*/*:latest" -f "reference=*/*:$BACKEND_VERSION"