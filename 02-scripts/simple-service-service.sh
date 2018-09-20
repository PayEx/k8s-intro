#!/bin/bash
while true
# GKE specific
do curl -s --max-time 1 "http://$(kubectl get service simple-service -o jsonpath='{.status.loadBalancer.ingress[*].ip}')"
sleep .5
done
