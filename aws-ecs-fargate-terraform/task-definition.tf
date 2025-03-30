# task_definition.tf
resource "aws_ecs_task_definition" "riseroots_fargate_task" {
  family                   = "riseroots-fargate-task"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = var.task_cpu  # No quotes
  memory                   = var.task_memory  # No quotes

  container_definitions = jsonencode([
    {
      name         = "riseroots-container"
      image        = var.container_image
      cpu          = var.task_cpu  # No quotes
      memory       = var.task_memory  # No quotes
      essential    = true
      portMappings = [{
        containerPort = var.container_port
        hostPort      = var.container_port
      }]
    }
  ])
}