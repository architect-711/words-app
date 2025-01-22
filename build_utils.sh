#!/usr/bin/zsh

function fallback_function() {
    echo "$1"
    return 1
}

# FIRST argument is the main .env file, since it
# can contain refers to sub-env files.
# The .env's file vars will be important at the end.
# The second argument is the list of another env files
function import_all_env_vars() {
    local main_env="$1"
    shift 1

    for file in "$@" ; do
        export_from_env "$file"
    done

    export_from_env "$main_env"
}

function export_from_env() {
    export $(grep -h -v '^#' "$1" | xargs)
}

function check_env_files() {
    if [ $(find "$1" -maxdepth 1 -name ".env*" -printf '.' | wc -m) -lt 3 ]; then
        fallback_function 'Found less than 3 .env files. Failing...'
    fi
}
