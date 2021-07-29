# syntax=docker/dockerfile:1
FROM alpine
RUN apk add openjdk11-jre
RUN apk add --no-cache sqlite
RUN addgroup -S dropwizard && adduser -S dropwizard -G dropwizard
USER dropwizard:dropwizard
ARG JAR_FILE=target/sy-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY sy-docker.yml sy.yml
RUN java -jar /app.jar db migrate sy.yml
EXPOSE 8081
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar", "server", "sy.yml"]
