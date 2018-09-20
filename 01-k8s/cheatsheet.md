# Cheatsheat for demo

## simple-service
1. Create a deployment: `kubectl apply -f deployments/simple-service.yml`
2. Expose a pod: `kubectl port-forward simple-service-<someid> 9000:9000`
3. Run request directly: `./02-scripts/simple-service.sh`
4. Abort request and add a service: `kubectl apply -f services/simple-service.yml`
5. Wait for LoadBalancer to be provisioned and run `./02-scripts/simple-service-service.sh`
6. Scale `kubectl scale --replicas=40 deployments/simple-service` and observe the results
7. Scale back down to 5
8. Perform a rolling update: `kubectl set image deployments/simple-service simple-service=evenh/simple-service:2.0.0`
    - Show history: `kubectl rollout history deployment/simple-service`
    - Perform rollback `kubectl rollout undo deployment/simple-service`

## customer-service

1. Deploy database secret: `kubectl apply -f secrets/customer-secret.yml`
2. Deploy service: `kubectl apply -f deployments/customer-service.yml -f services/customer-service.yml`
3. SSH into a random pod: `kubectl exec -it $(kubectl get pods -l app=simple-service -o jsonpath='{.items[0].metadata.name}') -- /bin/bash`
4. See that we can reach the customer service: `curl -D - http://customer-service/api/customers; echo`