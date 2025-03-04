FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/microservicio-buscador.jar microservicio-buscador.jar
EXPOSE 8081
ENTRYPOINT  ["java", "-jar", "microservicio-buscador.jar", "--server.port=${PORT:8081}"]
