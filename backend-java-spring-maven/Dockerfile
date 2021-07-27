# syntax=docker/dockerfile:1
FROM openjdk:8-jdk-alpine
RUN apk add --no-cache sqlite
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
