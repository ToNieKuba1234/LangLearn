FROM alpine:latest
WORKDIR /app

RUN apk update && apk upgrade
RUN apk add --no-cache openjdk25-jre

COPY target/*.jar langlearn-backend.jar

EXPOSE 3000
ENTRYPOINT ["java", "-jar", "langlearn-backend.jar"]