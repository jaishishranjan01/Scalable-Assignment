#!/bin/bash

set -e

echo "Starting Minikube with Docker driver..."
minikube start --driver=docker

echo "Configuring Docker to use Minikube's environment..."
eval $(minikube docker-env)

echo "Building Docker images inside Minikube..."
docker build -t auth-service:1.0 ./authentication
docker build -t train-service:1.0 ./trainservice
docker build -t payment-service:1.0 ./payment
docker build -t etrain-frontend:1.0 ./etrain

echo "Applying Kubernetes manifests..."
kubectl apply -f ./k8s/etrain-ingress.yaml
kubectl apply -f ./k8s/frontend-env-config.yaml
kubectl apply -f ./k8s/auth-service.yaml
kubectl apply -f ./k8s/train-service.yaml
kubectl apply -f ./k8s/payment-service.yaml
kubectl apply -f ./k8s/etrain-frontend.yaml

echo "Accessing frontend service..."
minikube tunnel

echo "Launching Kubernetes dashboard..."
minikube dashboard
