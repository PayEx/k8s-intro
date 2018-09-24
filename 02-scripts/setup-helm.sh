#!/bin/bash

# Setup RBAC
kubectl apply -f ../01-k8s/01-helm/helm-rbac-config.yml

# Install Helm server side
helm init --service-account tiller

echo "Sleeping for 30 seconds while awaiting Helm server side component"
sleep 30

# Install nginx ingress
helm install stable/nginx-ingress --name nginx-ingress -f ../01-k8s/01-helm/nginx-values.yml

# Install cert-manager for automatic Let's Encrypt TLS certificates
helm install stable/cert-manager --namespace kube-system --name cert-manager -f ../01-k8s/01-helm/nginx-values.yml

# Install cluster wide certificate issuer (Let's Encrypt production)
kubectl apply -f ../01-k8s/01-helm/acme-cluster-issuer-production.yml
