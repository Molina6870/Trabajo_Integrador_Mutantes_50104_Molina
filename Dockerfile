# ----------------------------------------------------------------------
# STAGE 1: BUILD (Compilación con Maven)
# ----------------------------------------------------------------------
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Mover el JAR compilado, renombrándolo a app.jar y excluyendo archivos de documentación/fuente.
# Esto asegura que la etapa de ejecución siempre encuentre 'app.jar'.
RUN mkdir -p build_output && \
    cp $(find target -maxdepth 1 -name "*.jar" ! -name "*sources.jar" ! -name "*javadoc.jar") build_output/app.jar


# ----------------------------------------------------------------------
# STAGE 2: RUN (Ejecución con JDK, la corrección final)
# ----------------------------------------------------------------------
# Usamos JDK-slim para asegurar que todas las clases de JPA/Hibernate estén disponibles.
FROM eclipse-temurin:21-jdk
EXPOSE 8080
WORKDIR /app

# Copia el JAR renombrado de la etapa de construcción
COPY --from=build /app/build_output/app.jar .

ENTRYPOINT ["java", "-jar", "app.jar"]
