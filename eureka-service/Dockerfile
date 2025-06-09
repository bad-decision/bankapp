FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /eureka

COPY .. ./

RUN gradle :eureka-service:clean :eureka-service:bootJar -x test

FROM openjdk:21

WORKDIR /eureka

COPY --from=builder /eureka/eureka-service/build/libs/eureka-service-0.0.1-SNAPSHOT.jar /eureka/eureka.jar

ENTRYPOINT ["java", "-jar", "/eureka/eureka.jar"]