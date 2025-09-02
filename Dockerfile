# Etapa 1: build do projeto usando Maven
FROM maven:3.9.3-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia todo o projeto de uma vez (incluindo mvnw e pom.xml)
COPY e-commerce .

# Garante permissão do mvnw (caso seja necessário)
RUN chmod +x mvnw

# Build do jar (sem testes)
RUN ./mvnw clean package -DskipTests

# Etapa 2: imagem leve para rodar o .jar
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia o .jar gerado na etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Porta que o Spring Boot vai expor
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
