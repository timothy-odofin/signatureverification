# Use the official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file and download the dependencies
COPY pom.xml .

# Copy the entire source code
COPY . .

# Build the application
RUN mvn clean install -DskipTests

# Use the official OpenJDK 17 with HotSpot JVM image as the base image for the final container
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Spring Boot JAR file into the container from the build stage
COPY --from=build /app/api/target/api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot app is running on
EXPOSE 9599

# Define the command to run your Spring Boot app
CMD ["java", "-jar", "app.jar"]
