# variables.tf

variable "ecs_service_name" {
  description = "Name of the ECS Service"
  type        = string
}

variable "ecs_cluster_name" {
  description = "The name of the ECS cluster"
  type        = string
}

variable "subnet_ids" {
  description = "The subnet IDs for the ECS service"
  type        = list(string)
}

variable "security_group_ids" {
  description = "The security group IDs for the ECS service"
  type        = list(string)
}

variable "eureka_image" {
  description = "The image URL for the Eureka service"
  type        = string
}

variable "cronservice_image" {
  description = "The image URL for the Cronservice service"
  type        = string
}

variable "eureka_port" {
  description = "Port on which the container runs"
  type        = number
}

variable "cronservice_port" {
  description = "Port on which the cronservice runs"
  type        = number
}

variable "actuator_health_path" {
  description = "actuator health path"
  type        = string
}

variable "task_cpu" {
  description = "The amount of CPU for the ECS task"
  type        = number
  default     = 256
}

variable "task_memory" {
  description = "The amount of Memory for the ECS task"
  type        = number
  default     = 512
}

variable "container_memory" {
  description = "The amount of Memory for the ECS task"
  type        = number
  default     = 512
}

variable "container_port" {
  description = "Port on which the container will listen"
  type        = number
  default     = 8080
}

variable "vpc_id" {
  description = "VPC ID where ECS will be deployed"
  type        = string
}

variable "desired_count" {
  description = "desired count to run number if instances with in cluster"
  type        = number
}

variable "eureka_image_tag" {
  description = "The tag of the eureka server image in ECR"
  type        = string
  default     = "eureka-server"
}

variable "cronservice_image_tag" {
  description = "The tag of the cronservice image in ECR"
  type        = string
  default     = "cronservice"
}