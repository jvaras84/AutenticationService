# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado en el directorio de trabajo del contenedor
COPY target/*.jar authenticator-app.jar

# Exponer el puerto en el que Spring Boot está configurado para ejecutarse (por defecto, 8080)
EXPOSE 8081

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "authenticator-app.jar"]