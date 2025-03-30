# terraform.tfvars

ecs_cluster_name   = "riseroots_cluster"
ecs_service_name   = "riseroots_fargate-service"
subnet_ids         = ["subnet-054095bb0fc85b0fd", "subnet-0314c30fb0a6b6cb0"]
security_group_ids = ["sg-0bccf62b5cfd66b4b"]

eureka_image      = "746038386741.dkr.ecr.eu-central-1.amazonaws.com/riseroots_repository:eureka-server"
cronservice_image = "746038386741.dkr.ecr.eu-central-1.amazonaws.com/riseroots_repository:cronservice"


eureka_port      = 8761
cronservice_port = 8080

task_cpu       = 256
container_port = 8080
container_memory = 512

vpc_id = "vpc-068aa14213b702676"

actuator_health_path = "/actuator/health"

desired_count = 0 # this is assential to make 0 to stop and delete the cluster and tasks, and 1 to setup and start again

eureka_image_tag = "eureka-server"
cronservice_image_tag = "cronservice"