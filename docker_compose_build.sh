#!/bin/bash

# Check for .env file existence
if [ ! -f ".env" ]; then
    echo ".env file not found."
    exit 1;
fi

# Share env vars
cp .env backend/.env
source .env

# Build app
cd "backend" && ./gradlew clean build -PBACKEND_VERSION="${BACKEND_VERSION}" && cd ".."

# Build services
docker compose build

# Remove temp .env file
rm backend/.env
