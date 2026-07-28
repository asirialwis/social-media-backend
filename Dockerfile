
# Stage 1: Build the Application JAR

FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copy dependency management files first to leverage Docker layer caching
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline -B

# Copy source code and build executable JAR
COPY src src
RUN ./mvnw clean package -DskipTests -B


# Stage 2: Production Lightweight Runtime

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create non-root user for security compliance (Least Privilege Principle)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Create Oracle Wallet directory structure inside container
RUN mkdir -p /app/oracle/wallet && chown -R appuser:appgroup /app/oracle

# Copy extracted application JAR from Stage 1
COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appgroup /app/app.jar

# Switch to non-root user
USER appuser

# Expose standard Spring Boot HTTP port
EXPOSE 8080

# Configure JVM tuning for container resource limits
ENTRYPOINT ["java", \
            "-XX:+UseG1GC", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", \
            "app.jar"]