#!/bin/bash

# Dashboard public access
kubectl delete ingress dashboard

apps=(simple-service dashboard shopping-cart-service customer-service mongodb ms-sql)
helm_apps=(cert-manager nginx-ingress)

for i in ${apps[@]}; do
    kubectl delete deployment $i
    kubectl delete service $i
done

# Helm specific
for i in ${helm_apps[@]}; do
    helm del --purge $i
done

# Delete Helm
helm reset