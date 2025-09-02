# Etapa 1: build com Maven
FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copia todos os arquivos do projeto (pom.xml, src/, etc.)
COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: imagem leve para rodar o .jar
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o .jar gerado na pasta /target da etapa de build
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
