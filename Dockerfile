FROM adoptopenjdk:11-openj9-focal
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
