--Start Docker 
--minikube start

docker build -t eureka-server .

docker build -t api-gateway:latest ./api-gateway
docker build -t eureka-server:latest ./eureka-server
docker build -t user-management:latest ./user-management
docker build -t customer-management:latest ./customer-management
docker build -t cronservice:latest ./cronservice

kubectl apply -f ./eureka-server/kubernetes/
kubectl apply -f ./api-gateway/kubernetes/
kubectl apply -f ./user-management/kubernetes/
kubectl apply -f ./customer-management/kubernetes/
kubectl apply -f ./cronservice/kubernetes/

kubectl apply -f ./api-gateway/kubernetes/api-gateway-deployment.yaml
kubectl apply -f ./eureka-server/kubernetes/eureka-server-deployment.yaml
kubectl apply -f ./user-management/kubernetes/user-management-deployment.yaml
kubectl apply -f ./customer-management/kubernetes/customer-management-deployment.yaml
kubectl apply -f ./cronservice/kubernetes/cronservice-deployment.yaml


kubectl apply -f ./api-gateway/kubernetes/api-gateway-service.yaml
kubectl apply -f ./eureka-server/kubernetes/eureka-server-service.yaml
kubectl apply -f ./user-management/kubernetes/user-management-service.yaml
kubectl apply -f ./customer-management/kubernetes/customer-management-service.yaml
kubectl apply -f ./cronservice/kubernetes/cronservice-service.yaml


kubectl rollout restart deployment api-gateway
kubectl rollout restart deployment eureka-server
kubectl rollout restart deployment cronservice
kubectl rollout restart deployment user-management
kubectl rollout restart deployment customer-management
kubectl rollout restart deployment cronservice


minikube status

minikube start --driver=docker

kubectl cluster-info

kubectl config current-context

kubectl get pods

kubectl get deployments

kubectl get services

kubectl describe pod <pod-name>
kubectl describe pod api-gateway-79896d547b-xvxfj

http://127.0.0.1:51450/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/workloads?namespace=default