FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/subtracker-1.0.0.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]