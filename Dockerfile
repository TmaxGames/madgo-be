# App
FROM eclipse-temurin:17-jre-alpine

WORKDIR /root

ENV PROFILE deploy
ENV PORT 8081

EXPOSE ${PORT}

RUN apk add --no-cache tzdata

COPY ./build/libs/*.jar /root/app.jar

ENTRYPOINT [ "java", "-DSpring.profiles.active=${PROFILE}", "-jar", "/root/app.jar" ]