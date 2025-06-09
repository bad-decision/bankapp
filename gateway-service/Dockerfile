FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /gateway

COPY .. ./

RUN gradle :gateway-service:clean :gateway-service:bootJar -x test

FROM openjdk:21

WORKDIR /gateway

COPY --from=builder /gateway/gateway-service/build/libs/gateway-service-0.0.1-SNAPSHOT.jar /gateway/gateway.jar

ENTRYPOINT ["java", "-jar", "/gateway/gateway.jar"]