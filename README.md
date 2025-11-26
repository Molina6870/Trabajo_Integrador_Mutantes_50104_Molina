# DATOS DEL ALUMNO
Nombre y Apellido: Juan Ignacio Molina
Legajo: 50104
Comisión: 3K10

# Mutant Detector API - Examen Técnico MercadoLibre

**Proyecto:** API REST para detectar si una secuencia de ADN pertenece a un humano o a un mutante, y persistir las estadísticas. Desarrollado como parte del examen técnico para Backend Developer.

---

# Tabla de Contenidos
1. [Requisitos Previos](#requisitos-previos)
2. [Ejecución Local](#ejecución-local)
3. [Endpoints de la API](#endpoints-de-la-api)
4. [Estructura del Proyecto y Tecnologías](#estructura-del-proyecto-y-tecnologías)
5. [Despliegue en Render](#despliegue-en-render)

---

## Requisitos Previos
Para ejecutar la aplicación, necesitas tener instalado:
- Java Development Kit (JDK) 21+
- Apache Maven 3.x
- (Opcional) Un IDE como IntelliJ IDEA o VS Code
- Docker (opcional, para construir y ejecutar la imagen Docker)

---

## Ejecución Local
Sigue estos pasos para compilar y levantar el proyecto en tu máquina:

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Molina6870/TrabajoIntegrador_Mutantes_50104.git

2. **Compilar y ejecutar test**
    Utiliza Maven para limpiar, compilar y ejecutar todos los test unitarios y de integración (mvn clean install).

3. **Ejecutar la aplicación SpringBoot**
    Una vez que la compilación sea exitosa, ejecuta el archivo JAR generado:
java -jar target/mutant-detector-0.0.1-SNAPSHOT.jar

    La API estará disponible en http://localhost:8080. La base de datos H2 se inicializa automáticamente en memoria

---
## Endpoints de la API
1. POST/mutant (Detección): Recibe un array de ADN, determina si es mutante (4 secuencias iguales en horizontal, vertical o diagonal), guarda el registro en la base de datos y responde con el estado HTTP correspondiente.

* Parámetro: dna
    * Tipo: List<String>
    * Descripción: Array de cadenas que forman la matriz N x N del ADN. Solo caracteres permitidos: A, T, C, G.

* Ejemplo de Solicitud
{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]

}

* Respuestas HTTP:
      * HTTP 200 Ok: El ADN es mutante.
      * HTTP 403 Forbidden: El ADN es humano.
      * HTTP 400 Bad Request: Error de formato.

2. GET/stats
    Devuelve las estadísticas de todas las verificaciones de ADN almacenadas en la base de datos hasta el momento.
* Endpoint: Get/stats
* Ejemplo de Respuesta:
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}

---
# Estructura del Proyecto y Tecnologías
src/
├── main/
│   ├── java/
│   │   └── com.mutant.mutant_detector/
│   │       ├── controller/
│   │       │     └── MutantController.java
│   │       │
│   │       ├── dto/
│   │       │     ├── MutantRequest.java
│   │       │     └── StatsResponse.java
│   │       │
│   │       ├── exception/
│   │       │     └── GlobalExceptionHandler.java
│   │       │
│   │       ├── model/
│   │       │     └── DnaRecord.java
│   │       │
│   │       ├── repository/
│   │       │     └── DnaRecordRepository.java
│   │       │
│   │       ├── service/
│   │       │     ├── MutantDetector.java
│   │       │     ├── MutantService.java
│   │       │     └── StatsService.java
│   │       │
│   │       └── MutantDetectorApplication.java
│   │
│   └── resources/
│        ├── static/
│        ├── templates/
│        └── application.properties
│
└── test/
    └── java/
        └── com.mutant.mutant_detector/
            ├── controller/
            │     └── MutantControllerIntegrationTest.java
            │
            ├── service/
            │     └── MutantDetectorTest.java
            │
            └── MutantDetectorApplicationTests.java

<img width="371" height="1145" alt="image" src="https://github.com/user-attachments/assets/1bc978d9-7795-4956-88e0-f9b8b2c6dc3e" />

---
# Tecnologías Clave
    * Lenguaje: Java 21+
    * Framework: Spring Boot 3
    * Build Tool: Apache Maven
    * Persistencia: Spring Data JPA + H2 Database
    * Testing: JUnit 5, Mockito
    * Optimización: Algoritmo de detección con Early Exit
    * Docker: Para despliegue en contenedores

---
# Despliegue en Render
    La API se encuentra hosteada y operativa como un servicio web. Puedes acceder a ella en la siguiente URL: https://trabajo-integrador-mutantes-50104-molina-92so.onrender.com

    * URL Base:
    * Endpoints:
        - POST/mutant: https://trabajo-integrador-mutantes-50104-molina-92so.onrender.com/mutant
        - GET/stats: https://trabajo-integrador-mutantes-50104-molina-92so.onrender.com/stats

