FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /blocker

COPY .. ./

RUN gradle :blocker-service:clean :blocker-service:bootJar -x test

FROM openjdk:21

WORKDIR /blocker

COPY --from=builder /blocker/blocker-service/build/libs/blocker-service-0.0.1-SNAPSHOT.jar /blocker/blocker.jar

ENTRYPOINT ["java", "-jar", "/blocker/blocker.jar"]