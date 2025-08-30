ARG BASE_IMAGE=eclipse-temurin:8-jre-alpine
FROM ${BASE_IMAGE}

RUN mkdir -p /app/logs
RUN mkdir -p /app/config
WORKDIR /app

ENV SERVER_PORT=8080

VOLUME /app/logs
VOLUME /app/config

EXPOSE ${SERVER_PORT}

ADD ./ruoyi.jar ./app.jar

ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dserver.port=${SERVER_PORT}", \
            "-jar", "app.jar", \
            "--spring.config.additional-location=./config/"
            ]
