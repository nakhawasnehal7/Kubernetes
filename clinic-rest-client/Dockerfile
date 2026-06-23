# Use Java 21 base image
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/clinic-rest-client-1.0.0-jar-with-dependencies.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]