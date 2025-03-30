variable "region" {
  description = "AWS region"
  type        = string
  default     = "ap-south-1"
}

variable "eks_role_name" {
  description = "IAM role name for EKS cluster"
  type        = string
  default     = "eks-cluster-role"
}

variable "eks_role_managed_policies" {
  description = "List of managed IAM policies for the EKS role"
  type        = list(string)
  default     = ["arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"]
}

variable "eks_cluster_name" {
  description = "EKS cluster name"
  type        = string
  default     = "riseroots-eks-cluster"
}

variable "fargate_role_name" {
  description = "IAM role name for Fargate execution"
  type        = string
  default     = "eks-fargate-role"
}

variable "fargate_role_managed_policies" {
  description = "List of managed IAM policies for the Fargate role"
  type        = list(string)
  default     = ["arn:aws:iam::aws:policy/AmazonEKSFargatePodExecutionRolePolicy"]
}

variable "fargate_profile_name" {
  description = "Name of the Fargate profile"
  type        = string
  default     = "default"
}

variable "vpc_name" {
  description = "The name of the VPC to create subnets in"
  type        = string
  default     = "riseroots_vpc"  # You can set a default VPC name if you have one
}
