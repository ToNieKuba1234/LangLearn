#!/bin/bash

echo "Building docker image for DB..."
docker build -t langlearn-db:latest .