FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY pom.xml ./
COPY src ./src
# Built by the parent Maven reactor; this stage assumes a pre-built jar is copied in CI.
# For standalone builds: mvn -pl patient-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/patient-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
