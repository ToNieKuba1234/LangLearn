FROM alpine:latest
WORKDIR /app

RUN apk update && apk upgrade
RUN apk add --no-cache openjdk25-jre

COPY target/*.jar langlearn-frontend.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "langlearn-frontend.jar"]