# syntax=docker/dockerfile:1
FROM openjdk:8-jdk-alpine
RUN apk add --no-cache sqlite
RUN addgroup -S dropwizard && adduser -S dropwizard -G dropwizard
USER spring:spring
ARG JAR_FILE=target/${project.artifactId}-${project.version}.jar
COPY ${JAR_FILE} app.jar
COPY sy-docker.yml sy.yml
RUN java -jar /app.jar db migrate sy.yml
EXPOSE 8081
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar", "server", "sy.yml"]
