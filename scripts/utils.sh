#!/usr/bin/env bash

function source_dev() {
  set -a
  source ../.env.base
  source ../.env.dev
  set +a
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