# Use OpenJDK 17 slim version as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file using a wildcard pattern
COPY target/user*management*.jar /app/user-management.jar

# Expose the service port
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/user-management.jar"]
