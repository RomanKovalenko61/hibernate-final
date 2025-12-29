FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY . /workspace

RUN mvn -B -DskipTests package



FROM eclipse-temurin:17-jre
WORKDIR /app

ARG JAR_NAME=hibernate-final-0.0.1-SNAPSHOT.jar
COPY --from=build /workspace/target/${JAR_NAME} /app/app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

