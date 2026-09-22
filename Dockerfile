FROM maven:3.9.11-eclipse-temurin-25 AS build

WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -B dependency:go-offline

COPY src/ src/
RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /workspace/target/*.jar app.jar
RUN mkdir -p /app/output

ENV PROCESSING_OUTPUT_FILE=/app/output/transaction-results.csv

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]