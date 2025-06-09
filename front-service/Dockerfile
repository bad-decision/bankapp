FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /front

COPY . ./

RUN gradle :front-service:clean :front-service:bootJar -x test

FROM openjdk:21

WORKDIR /front

COPY --from=builder /front/front-service/build/libs/front-service-0.0.1-SNAPSHOT.jar /front/front.jar

ENTRYPOINT ["java", "-jar", "/front/front.jar"]