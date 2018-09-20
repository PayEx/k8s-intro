#!/bin/bash
while true
# GKE specific
do curl "http://$(kubectl get service simple-service -o jsonpath='{.status.loadBalancer.ingress[*].ip}')"
sleep .5
done