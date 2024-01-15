# App
FROM eclipse-temurin:17-jre-alpine

WORKDIR /root

ARG NDB_PASSWORD

ENV PROFILE deploy

EXPOSE ${PORT}

RUN apk add --no-cache tzdata

COPY ./build/libs/*.jar /root/app.jar

ENTRYPOINT [ "java", "-DSpring.profiles.active=${PROFILE}", "-jar", "/root/app.jar" ]