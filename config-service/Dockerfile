FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /config

COPY .. ./

RUN gradle :config-service:clean :config-service:bootJar -x test

FROM openjdk:21

WORKDIR /config

COPY --from=builder /config/config-service/build/libs/config-service-0.0.1-SNAPSHOT.jar /config/config.jar

ENTRYPOINT ["java", "-jar", "/config/config.jar"]