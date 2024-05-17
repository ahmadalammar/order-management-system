FROM maven:3.8.4-openjdk-11-slim AS build
# Set the working directory in the container
WORKDIR /payment-service
# Build the application using Maven
RUN mvn clean package -DskipTests

# the base image
FROM amazoncorretto:17

# the JAR file path
ARG JAR_FILE=target/*.jar

# Copy the JAR file from the build context into the Docker image
COPY ${JAR_FILE} payment-service.jar

CMD apt-get update -y

# Set the default command to run the Java application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/payment-service.jar"]