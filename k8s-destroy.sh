#!/bin/bash

kubectl delete -f k8s-setup.yaml --ignore-not-found=true

kubectl delete deployments --all
kubectl delete services --all

kubectl delete pvc --all

minikube stop