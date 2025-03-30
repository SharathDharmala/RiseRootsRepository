
aws ecr create-repository --repository-name riseroots_repository --region eu-central-1

aws ecr describe-repositories --region eu-central-1

docker tag eureka-server:latest 746038386741.dkr.ecr.eu-central-1.amazonaws.com/riseroots_repository:latest
docker tag cronservice:latest 746038386741.dkr.ecr.eu-central-1.amazonaws.com/riseroots_repository:latest

docker push 746038386741.dkr.ecr.eu-central-1.amazonaws.com/riseroots_repository:latest