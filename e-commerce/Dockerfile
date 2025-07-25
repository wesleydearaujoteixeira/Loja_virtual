# Etapa 1: Build com Maven
FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copia todos os arquivos
COPY . .

# Build com Maven
RUN mvn clean package -DskipTests

# Etapa 2: imagem mais leve para rodar a aplicação
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o .jar da primeira etapa
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
