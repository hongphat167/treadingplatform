## Build Stage ##
FROM gradle:7.3.1-jdk17-alpine as build
WORKDIR /app

# Copy only the necessary Gradle wrapper files first to leverage Docker layer caching
COPY gradlew /app/gradlew

# Make the Gradle wrapper script executable
RUN chmod +x /app/gradlew

# Copy the rest of the project files
COPY . .

# Run the build command
RUN ./gradlew clean build -x test --no-daemon --stacktrace --info

## Run Stage ##
FROM openjdk:17-jdk-alpine
WORKDIR /run
COPY --from=build /app/build/libs/coin-0.0.1-SNAPSHOT.jar /run/coin-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/run/coin-0.0.1-SNAPSHOT.jar"]
