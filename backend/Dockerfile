FROM openjdk:24-oraclelinux9

# Need during both states
ARG APP_NAME
ENV APP_NAME_ENV=$APP_NAME


# Needed only on build
ARG DEST_FOLDER=/opt/words-app/backend
ARG APP_VERSION


# Metadata for image
LABEL author="https://github.com/architect-711"
LABEL source_code="https://github.com/architect-711/words-app"
LABEL version=$APP_VERSION
LABEL docs="https://app.weeek.net/ws/688666/kb/19"


# Copy build file
COPY /build/libs/$APP_NAME $DEST_FOLDER/$APP_NAME

# Set working directory
WORKDIR $DEST_FOLDER

# Run app
ENTRYPOINT ["sh", "-c", "java --enable-native-access=ALL-UNNAMED -jar $APP_NAME_ENV --spring.profiles.active=prod"]