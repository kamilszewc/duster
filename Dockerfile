FROM adoptopenjdk:11-jre-openj9-focal
COPY --from=build build/libs/*.jar app.jar
