FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /exchange-generator

COPY .. ./

RUN gradle :exchange-generator-service:clean :exchange-generator-service:bootJar -x test

FROM openjdk:21

WORKDIR /exchange-generator

COPY --from=builder /exchange-generator/exchange-generator-service/build/libs/exchange-generator-service-0.0.1-SNAPSHOT.jar /exchange-generator/exchange-generator.jar

ENTRYPOINT ["java", "-jar", "/exchange-generator/exchange-generator.jar"]