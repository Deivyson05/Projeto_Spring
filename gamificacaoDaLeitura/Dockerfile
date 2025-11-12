# Usa imagem oficial do Java 17 com Gradle
FROM gradle:8.5-jdk17-alpine AS build

# Define diretório de trabalho
WORKDIR /app

# Copia apenas arquivos necessários para o build
COPY build.gradle settings.gradle ./
COPY src ./src

# Compila o projeto
RUN gradle clean build -x test --no-daemon

# Segunda etapa: imagem final menor
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia apenas o JAR da etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta
EXPOSE 8080

# Comando para iniciar
CMD ["java", "-Dserver.port=${PORT}", "-Dspring.profiles.active=prod", "-jar", "app.jar"]