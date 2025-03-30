variable "subnets" {
  description = "The subnets for the ECS service"
  type        = list(string)
}

variable "security_groups" {
  description = "The security groups for the ECS service"
  type        = list(string)
}

variable "ecs_cluster_name" {
  description = "Name of the ECS Cluster"
  type        = string
}

variable "ecs_service_name" {
  description = "Name of the ECS Service"
  type        = string
}

variable "task_cpu" {
  description = "The amount of CPU to allocate to the task"
  type        = number
}

variable "task_memory" {
  description = "The amount of memory to allocate to the task"
  type        = number
}

variable "container_image" {
  description = "Container Image for ECS"
  type        = string
}

variable "container_port" {
  description = "Port on which the container runs"
  type        = number
}

variable "desired_count" {
  description = "Number of tasks to run in the ECS service"
  type	=	number
  default     = 0
}

