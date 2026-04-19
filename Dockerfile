FROM openjdk:21-jdk-alpine

WORKDIR /app

COPY target/user-0.0.1-SNAPSHOT.jar  /app/user.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/user.jar"]