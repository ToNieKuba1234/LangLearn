#!/bin/bash

echo "Building Tailwind..."
pushd src/main/resources/static > /dev/null
    npm run build
popd > /dev/null

echo "Building JAR..."
mvn clean package -DskipTests

echo "Building Docker Image..."
docker build -t langlearn-frontend:latest .
