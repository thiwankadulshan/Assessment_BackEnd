FROM maven:3.9.0-eclipse-temurin-17 AS build

RUN apt-get update && apt-get install -y openjdk-21-jdk

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

ENV MAVEN_OPTS="-Dmaven.compiler.source=21 -Dmaven.compiler.target=21"

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/entgra-0.0.1-SNAPSHOT.jar app.jar

RUN mkdir -p /app/logs

ENV SPRING_PROFILES_ACTIVE=dev

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--logging.file.path=/app/logs"]
