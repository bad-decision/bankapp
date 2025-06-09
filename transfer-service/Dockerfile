FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /transfer

COPY .. ./

RUN gradle :transfer-service:clean :transfer-service:bootJar -x test

FROM openjdk:21

WORKDIR /transfer

COPY --from=builder /transfer/transfer-service/build/libs/transfer-service-0.0.1-SNAPSHOT.jar /transfer/transfer.jar

ENTRYPOINT ["java", "-jar", "/transfer/transfer.jar"]