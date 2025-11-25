# DATOS DEL ALUMNO
Nombre y Apellido: Juan Ignacio Molina
Legajo: 50104
Comisión: 3K10

# Mutant Detector API - Examen Técnico MercadoLibre
    Proyecto: API REST para detectar si una secuencia de ADN pertenece a un humano o a un mutante, y persistir las estadísticas. Desarrollado como parte del examen técnico para Backend Developer.

# Tabla de Contenidos
    1. Requisitos Previos
    2. Ejecución Local
    3.Endpoints de la API
        *POST /mutant (Detección)
        *GET /stats (Estadísticas)
    4.Estructura del Proyecto y Tecnologías
    5.Despliegue en Render (URL)

# Requisitos Previos
    Para ejecutar la aplicación, necesitas tener instalado:
    * Java Development Kit (JDK) 17+
    * Apache Maven 3.x
    * (Opcional) Un IDE como IntelliJ IDEA o VS Code.

# Ejecución Local

Sigue estos pasos para compilar y levantar el proyecto en tu máquina:

1. Clonar el repositorio:
    git clone https://github.com/Molina6870/TrabajoIntegrador_Mutantes_50104.git

2. Compilar y ejecutar tests:
Utiliza Maven para limpiar, compilar y ejecutar todos los tests unitarios y de integración (asegurando el Code Coverage).
"mvn clean install"

3. Ejecutar la aplicación Spring Boot:
Una vez que la compilación sea exitosa, ejecuta el archivo JAR generado:
java -jar target/mutant-detector-0.0.1-SNAPSHOT.jar

La API estará disponible en http://localhost:8080. La base de datos H2 se inicializa automáticamente en memoria (h2:mem:testdb).

# Endpoints de la API

1. POST /mutant (Detección)

Recibe un array de ADN, determina si es mutante (4 secuencias iguales en horizontal, vertical o diagonal), guarda el registro en la base de datos y responde con el estado HTTP correspondiente.

Parámetro: dna
Tipo: List<String>
Descripción: Array de cadenas que forman la matriz N x N del ADN. Solo caracteres: A, T, C, G.

Solicitud (Ejemplo Mutante):

{
"dna": [
"ATGCGA",
"CAGTGC",
"TTATGT",
"AGACGG",
"CCCCTA",
"TCACTG"
]
}

Respuestas HTTP:

* HTTP 200 OK: El ADN es mutante.

* HTTP 403 Forbidden: El ADN es humano.

* HTTP 400 Bad Request: Error de formato (matriz no NxN, o caracteres inválidos).

2. GET /stats (Estadísticas)

Devuelve las estadísticas de todas las verificaciones de ADN almacenadas en la base de datos hasta el momento.

* Endpoint: GET /stats

Respuesta (Ejemplo):

{
"count_mutant_dna": 40,
"count_human_dna": 100,
"ratio": 0.4
}

# Estructura del Proyecto y Tecnologías

El proyecto sigue una arquitectura limpia en capas (Controller -> Service -> Repository) para separar las responsabilidades:

src/
├── main/
│   ├── java/
│   │   └── com.mutant.mutant_detector/
│   │        ├── controller/
│   │        │     └── MutantController.java
│   │        │
│   │        ├── dto/
│   │        │     ├── MutantRequest.java
│   │        │     └── StatsResponse.java
│   │        │
│   │        ├── exception/
│   │        │     └── GlobalExceptionHandler.java
│   │        │
│   │        ├── model/
│   │        │     └── DnaRecord.java
│   │        │
│   │        ├── repository/
│   │        │     └── DnaRecordRepository.java
│   │        │
│   │        ├── service/
│   │        │     ├── MutantDetector.java
│   │        │     ├── MutantService.java
│   │        │     └── StatsService.java
│   │        │
│   │        └── MutantDetectorApplication.java
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

# Tecnologías Clave:

* Lenguaje: Java 17+

* Framework: Spring Boot 3

* Build Tool: Apache Maven

* Persistencia: Spring Data JPA + H2 Database

* Testing: JUnit 5, Mockito

* Optimización: Algoritmo de detección con Early Exit.

# Despliegue en Render
La API se encuentra hosteada y operativa como un servicio web.



