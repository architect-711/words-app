#!/bin/bash

# First build project
cd backend ; ./gradlew build

# Get back
cd ..

# Run docker containers
sudo docker compose up
