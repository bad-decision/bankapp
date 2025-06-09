FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /notification

COPY .. ./

RUN gradle :notification-service:clean :notification-service:bootJar -x test

FROM openjdk:21

WORKDIR /notification

COPY --from=builder /notification/notification-service/build/libs/notification-service-0.0.1-SNAPSHOT.jar /notification/notification.jar

ENTRYPOINT ["java", "-jar", "/notification/notification.jar"]