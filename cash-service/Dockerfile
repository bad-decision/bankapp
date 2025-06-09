FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /cash

COPY .. ./

RUN gradle :cash-service:clean :cash-service:bootJar -x test

FROM openjdk:21

WORKDIR /cash

COPY --from=builder /cash/cash-service/build/libs/cash-service-0.0.1-SNAPSHOT.jar /cash/cash.jar

ENTRYPOINT ["java", "-jar", "/cash/cash.jar"]