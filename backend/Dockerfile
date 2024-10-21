FROM openjdk:24-jdk-slim

ARG PROJECT_DEST_FOLDER /usr/lib/words-app/backend

COPY /build/libs/*.jar $PROJECT_DEST_FOLDER/*.jar

WORKDIR $PROJECT_DEST_FOLDER

ENTRYPOINT ["java"]

CMD ["-jar", "*.jar"]