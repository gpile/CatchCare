# Use an official Maven image as the base image
FROM maven:3-eclipse-temurin-21 AS build
#FROM eclipse-temurin:21 AS build
# Set the working directory in the container
WORKDIR /build

# Copy the parent pom.xml and the project files to the container
# context is set to the root of the project in docker compose
COPY pom.xml .
COPY services/ ./services/

# Build the application using Maven
RUN mvn clean package -pl services/trap-service -am -DskipTests

# Use an official OpenJDK image as the base image
FROM eclipse-temurin:21-jre
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /build/services/trap-service/target/*.jar service.jar

# Set the command to run the application
CMD ["java", "-jar", "/app/service.jar"]

#EXPOSE 8081