FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/bed-service-*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "app.jar"]
