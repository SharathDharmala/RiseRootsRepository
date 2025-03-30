resource "aws_ecs_service" "riseroots_fargate_service" {
  name            = var.ecs_service_name
  cluster         = aws_ecs_cluster.riseroots_fargate_cluster.id
  task_definition = aws_ecs_task_definition.riseroots_fargate_task.arn
  launch_type     = "FARGATE"
  desired_count   = var.desired_count
  
  network_configuration {
    subnets         = var.subnets
    security_groups = var.security_groups
    assign_public_ip = true
  }
}