# ----------------------------------------------------------------------
# STAGE 1: BUILD
# ----------------------------------------------------------------------
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Mover el JAR compilado (evitando sources/javadoc)
RUN mkdir -p build_output && \
    cp $(find target -maxdepth 1 -name "*.jar" ! -name "*sources.jar" ! -name "*javadoc.jar") build_output/app.jar


# ----------------------------------------------------------------------
# STAGE 2: RUN
# ----------------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
WORKDIR /app

COPY --from=build /app/build_output/app.jar .

ENTRYPOINT ["java", "-jar", "app.jar"]
