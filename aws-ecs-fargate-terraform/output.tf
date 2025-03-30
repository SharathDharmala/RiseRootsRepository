# outputs.tf
output "ecs_cluster_name" {
  value = aws_ecs_cluster.riseroots_fargate_cluster.name
}

output "ecs_service_name" {
  value = aws_ecs_service.riseroots_fargate_service.name
}

output "cluster_desired_count_status" {
  value = aws_ecs_service.riseroots_fargate_service.desired_count > 0 ? "Cluster will get Active mode" : "Cluster will get down in a moment.."
}