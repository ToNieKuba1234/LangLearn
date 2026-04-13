#!/bin/bash
DB_URL=$(minikube service langlearn-db-service --url | sed 's/http/jdbc:postgresql/')

flyway migrate -url=$DB_URL -user=user -password=pass -locations=filesystem:src/main/resources/db/migration