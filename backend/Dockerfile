FROM openjdk:24-oraclelinux9

# Metadata for image
LABEL author="https://github.com/architect-711"
LABEL source_code="https://github.com/architect-711/words-app"
LABEL docs="https://app.weeek.net/ws/688666/kb/12"
LABEL name="words-app-backend"

ARG DEST_FOLDER=/opt/words-app/backend

# Copy build file
COPY /build/libs/$APP_NAME_ENV $DEST_FOLDER/$APP_NAME_ENV

# Set working directory
WORKDIR $DEST_FOLDER

# Run app
ENTRYPOINT ["sh", "-c", "java --enable-native-access=ALL-UNNAMED -jar $APP_NAME_ENV --spring.profiles.active=$ACTIVE_PROFILE_ENV,postgres"]