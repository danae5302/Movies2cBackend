FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /movies2c_backend
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
WORKDIR /movies2c_backend
COPY --from=build /movies2c_backend/target/*.jar movies2c_backend.jar

#COPY --from=build /target/movies2c_backend-0.0.1-SNAPSHOT.jar movies2c_backend.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","movies2c_backend.jar"]