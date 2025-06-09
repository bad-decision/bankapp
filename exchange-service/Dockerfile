FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /exchange

COPY .. ./

RUN gradle :exchange-service:clean :exchange-service:bootJar -x test

FROM openjdk:21

WORKDIR /exchange

COPY --from=builder /exchange/exchange-service/build/libs/exchange-service-0.0.1-SNAPSHOT.jar /exchange/exchange.jar

ENTRYPOINT ["java", "-jar", "/exchange/exchange.jar"]