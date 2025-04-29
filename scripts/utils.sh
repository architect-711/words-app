#!/usr/bin/env bash

export SCRIPTS_FOLDER_REL=scripts
export BUILD_PATH_REL=../backend/build/libs
export BACKEND_FOLDER_REL=../backend

function source_dev() {
  set -a
  source ../.env.base
  source ../.env.dev
  set +a
}

function die() {
  echo "$1";
  exit 1;
}

function source_prod() {
  set -a
  source ../.env.base
  source ../.env.prod
  set +a
}

function in_dev_do() {
  source_dev

  "$@"
}