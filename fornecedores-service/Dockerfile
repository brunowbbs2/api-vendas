# --- Estágio 1: build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia código-fonte e gera o .jar
COPY src ./src
RUN mvn package -DskipTests -B

# --- Estágio 2: runtime ---
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Porta que o fornecedores-service utiliza
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]