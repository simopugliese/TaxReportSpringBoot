# Usa Java 21 su Alpine Linux (leggerissimo)
FROM eclipse-temurin:21-jre-alpine

# Crea una cartella per l'app
WORKDIR /app

# Copia il file jar compilato dentro il container
# NOTA: Il nome del file deve corrispondere a quello nel target di Maven
COPY taxreport-backend-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta
EXPOSE 8080

# Avvia l'app
ENTRYPOINT ["java", "-jar", "app.jar"]