# ----------------------------------------------------------------------
# STAGE 1: BUILD (Compilación con Maven)
# ----------------------------------------------------------------------
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------
# STAGE 2: RUN (Ejecución con JRE más estable)
# ----------------------------------------------------------------------
FROM eclipse-temurin:21-jre-slim
EXPOSE 8080
WORKDIR /app

# COPIA CRÍTICA: Copia el JAR con su nombre exacto
COPY --from=build /app/target/mutant-detector-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
