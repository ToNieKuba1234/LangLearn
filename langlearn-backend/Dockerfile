#Basic alpine
# FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY target/*.jar app.jar
#System update

#Install openjre
ENTRYPOINT ["java", "-jar", "app.jar"]

COPY target/*.jar app.jar