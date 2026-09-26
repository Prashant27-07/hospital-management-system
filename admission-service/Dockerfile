FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/admission-service-*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]
