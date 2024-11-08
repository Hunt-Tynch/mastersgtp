# Use a specific version of OpenJDK as the base image for the backend
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the backend project files to the container
COPY . /app

# Compile and package the application
RUN ./mvnw package -DskipTests

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=production

# Expose port 8080 for the backend
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "./target/mastersgtp-0.0.1-SNAPSHOT.jar"]
