#! /bin/bash

: "
  Only to test that docker successfully builds app.

  Created because when you manually build container,
  an error happens, because Docker can't find ../.env
  file since it's out of his context

  Docker compose doesn't require it, it automatically
  adds .env file

  First argument is the container title.
"

readonly ENV_FILE="../.env"

# Check for .env file existence
if [ ! -f "$ENV_FILE" ]; then
    echo "File not found $ENV_FILE"
    exit 1
fi

# Share .env vars
cp ../.env .env
source .env

# Build app
./gradlew clean build -PAPP_VERSION="${APP_VERSION}"

# Run Dockerfile
docker build \
    --rm \
    --build-arg APP_NAME="${APP_BUILD_NAME}" \
    --build-arg APP_VERSION="${APP_VERSION}" \
    -t "$1" \
    .

# Remove temp .env file
rm .env