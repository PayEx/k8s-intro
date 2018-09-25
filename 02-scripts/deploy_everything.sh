#!/bin/bash

# Install Helm for nginx/Let's Encrypt
./setup-helm.sh

# Simple service
kubectl apply -f ../01-k8s/deployments/simple-service.yml -f ../01-k8s/services/simple-service.yml
kubectl scale --replicas=3 deployments/simple-service

# Databases
kubectl apply -f ../01-k8s/mongodb.yml -f ../01-k8s/sqlserver.yml

# Customer service
kubectl apply -f ../01-k8s/secrets/customer-secret.yml
kubectl apply -f ../01-k8s/deployments/customer-service.yml -f ../01-k8s/services/customer-service.yml

# Shopping cart service
kubectl apply -f ../01-k8s/configmaps/shopping-cart-config.yml
kubectl apply -f ../01-k8s/deployments/shopping-cart-service.yml -f ../01-k8s/services/shopping-cart-service.yml

# Dashboard
kubectl apply -f ../01-k8s/deployments/dashboard.yml -f ../01-k8s/services/dashboard.yml
kubectl apply -f ../01-k8s/ingress/dashboard.yml

echo "All done, redirect traffic to:"
kubectl get service nginx-ingress-controller -o jsonpath='{.status.loadBalancer.ingress[*].ip}'
