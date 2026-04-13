#!/bin/bash

echo "Building .jar"
mvn clean package -DskipTests

echo "Building docker image"
docker build -t langlearn-backend:latest .