## Build Stage ##

FROM gradle:7.3.1-jdk17-alpine as build
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew clean build -x test --no-daemon --stacktrace --info

## Run Stage ##

FROM openjdk:17-jdk-alpine
WORKDIR /run
COPY --from=build /app/build/libs/coin-0.0.1-SNAPSHOT.jar /run/coin-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/run/coin-0.0.1-SNAPSHOT.jar"]