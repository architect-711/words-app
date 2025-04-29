#!/usr/bin/zsh

source utils.sh

# Import dev env vars
source_dev
# Build Spring app
cd "$BACKEND_FOLDER_REL" && ./gradlew clean test build && cd ..
# fail if build isn't succeeded
if [ "$?" -ne 0 ]; then
  die "App building has been successfully FAILED."
fi
# go to the folder with `.sh` scripts
cd "$SCRIPTS_FOLDER_REL" || die "--> Couldn't find folder with \`.sh\` scripts, specified -->$SCRIPTS_FOLDER_REL<--"

echo "--> App has been successfully build!"
echo "--> Version: $BACKEND_VERSION, build file: $(ls "$BUILD_PATH_REL")"
