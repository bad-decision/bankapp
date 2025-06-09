FROM gradle:8.12.1-jdk21-corretto as builder

WORKDIR /account

COPY .. ./

RUN gradle :account-service:clean :account-service:bootJar -x test

FROM openjdk:21

WORKDIR /account

COPY --from=builder /account/account-service/build/libs/account-service-0.0.1-SNAPSHOT.jar /account/account.jar

ENTRYPOINT ["java", "-jar", "/account/account.jar"]