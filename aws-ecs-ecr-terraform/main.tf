# main.tf

resource "aws_ecs_task_definition" "eureka_task" {
  family                   = "eureka-task"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = var.task_cpu    # No quotes
  memory                   = var.task_memory # No quotes

  execution_role_arn = "arn:aws:iam::746038386741:role/ecsTaskExecutionRole" # Using the existing role ARN here

  container_definitions = jsonencode([{
    name      = "eureka-server"
    image     = "${aws_ecr_repository.riseroots_repo.repository_url}:${var.eureka_image_tag}" # Dynamically using ECR repo and tag
    essential = true
    memory    = var.container_memory # Memory limit for the container
    portMappings = [
      { containerPort = var.eureka_port, hostPort = var.eureka_port }
    ]
  }])

}

resource "aws_ecs_task_definition" "cronservice_task" {
  family                   = "cronservice-task"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = var.task_cpu    # No quotes
  memory                   = var.task_memory # No quotes

  execution_role_arn = "arn:aws:iam::746038386741:role/ecsTaskExecutionRole" # Using the existing role ARN here

  container_definitions = jsonencode([{
    name      = "cronservice"
    image     = "${aws_ecr_repository.riseroots_repo.repository_url}:${var.cronservice_image_tag}"
    essential = true
    memory    = var.container_memory # Memory limit for the container
    portMappings = [
      { containerPort = var.cronservice_port, hostPort = var.cronservice_port }
    ]
  }])
}

resource "aws_ecs_service" "eureka_service" {
  name            = "eureka-service"
  cluster         = var.ecs_cluster_name # Use the variable here
  task_definition = aws_ecs_task_definition.eureka_task.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets          = var.subnet_ids         # Use the variable here
    security_groups  = var.security_group_ids # Use the variable here
    assign_public_ip = true
  }
}

resource "aws_ecs_service" "cronservice_service" {
  name            = "cronservice-service"
  cluster         = var.ecs_cluster_name # Use the variable here
  task_definition = aws_ecs_task_definition.cronservice_task.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets          = var.subnet_ids         # Use the variable here
    security_groups  = var.security_group_ids # Use the variable here
    assign_public_ip = true
  }
}
