FROM eclipse-temurin:17 AS builder
WORKDIR /workspace

COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /workspace/build/libs/borikkori-community-0.0.1.jar app.jar

# Expose the port that Spring Boot listens on (default 8080)
EXPOSE 8080

# Launch the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
