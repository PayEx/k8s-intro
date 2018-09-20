# Cheatsheat for demo

## simple-service
1. Create a deployment: `kubectl apply -f deployments/simple-service.yml`
2. Expose a pod: `kubectl port-forward simple-service-<someid> 9000:9000`
3. Run request directly: `./02-scripts/simple-service.sh`
4. Abort request and add a service: `kubectl apply -f services/simple-service.yml`
5. Wait for LoadBalancer to be provisioned and run `./02-scripts/simple-service-service.sh`
6. Scale `kubectl scale --replicas=40 deployments/simple-service` and observe the results
7. Scale back down to 3