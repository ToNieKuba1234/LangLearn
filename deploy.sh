#!/bin/bash
minikube start

eval $(minikube docker-env)

echo "Building Docker images"

pushd langlearn-backend > /dev/null 
    echo "BE..."; ./backend-build.sh
popd > /dev/null
pushd langlearn-frontend > /dev/null 
    echo "FE..."; ./frontend-build.sh
popd > /dev/null
pushd langlearn-db > /dev/null 
    echo "DB..."; ./db-build.sh
popd > /dev/null

kubectl apply -f k8s-setup.yaml

echo "Restarting Pods"
kubectl rollout restart deployment langlearn-db-dep
kubectl rollout restart deployment langlearn-backend-dep
kubectl rollout restart deployment langlearn-frontend-dep

echo "The website is available under this URL : "
minikube service langlearn-frontend-service --url