
# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/order-management-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
