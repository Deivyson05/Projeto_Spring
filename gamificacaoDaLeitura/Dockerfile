# Usa imagem oficial do Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define diretório de trabalho
WORKDIR /app

# Copia todo o projeto
COPY . .

# Dá permissão de execução ao gradlew
RUN chmod +x ./gradlew

# Compila o projeto (sem rodar testes)
RUN ./gradlew clean build -x test

# Expõe a porta
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-Dserver.port=${PORT}", "-Dspring.profiles.active=prod", "-jar", "build/libs/app.jar"]