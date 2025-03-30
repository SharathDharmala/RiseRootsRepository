# ecs_cluster.tf
resource "aws_ecs_cluster" "riseroots_fargate_cluster" {
  name = var.ecs_cluster_name
}
