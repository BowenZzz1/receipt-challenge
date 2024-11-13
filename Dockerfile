# Use a base image with OpenJDK 17
FROM openjdk:17-jdk-slim


# Set the working directory in the container
WORKDIR /app


# Copy the built JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar


# Expose the port
EXPOSE 8080


# Command to run the application
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
