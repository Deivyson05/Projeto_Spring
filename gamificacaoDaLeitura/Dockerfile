# ====== STAGE 1: BUILD ======
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia somente o pom para resolver dependências em cache
COPY pom.xml ./
RUN mvn -q -B -DskipTests dependency:go-offline

# Agora copia o código e empacota
COPY src ./src
RUN mvn -q -B -DskipTests package

# ====== STAGE 2: RUNTIME ======
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Variáveis úteis
ENV PORT=8080
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseContainerSupport"

# Copia o jar gerado
# Ajuste o nome se necessário; abaixo pega o primeiro *-SNAPSHOT.jar
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

# Força o Spring a usar a porta do Render
CMD sh -c "java $JAVA_OPTS -Dserver.port=${PORT} -jar /app/app.jar"
