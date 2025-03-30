provider "aws" {
  region = var.region
}

data "aws_vpc" "selected" {
  filter {
    name   = "tag:Name"
    values = [var.vpc_name]
  }
}

module "subnet_module" {
  source = "./subnet"
  vpc_id = data.aws_vpc.selected.id  # Pass the dynamically fetched VPC ID
}


# IAM Role for EKS Cluster
resource "aws_iam_role" "eks_role" {
  name = var.eks_role_name

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "eks.amazonaws.com"
      }
    }]
  })
}

# Attach managed policies to the EKS Role
resource "aws_iam_role_policy_attachment" "eks_role_attachment" {
  for_each   = toset(var.eks_role_managed_policies)
  role       = aws_iam_role.eks_role.name
  policy_arn = each.value
}

resource "aws_eks_cluster" "eks_cluster" {
  name     = "riseroots_cluster"
  role_arn = aws_iam_role.eks_cluster_role.arn

  vpc_config {
    subnet_ids = [
      module.subnet_module.public_subnet_1_id,
      module.subnet_module.public_subnet_2_id,
      module.subnet_module.private_subnet_1_id,
      module.subnet_module.private_subnet_2_id
    ]
    endpoint_public_access = true
    endpoint_private_access = true
  }

  depends_on = [aws_iam_role_policy_attachment.eks_role_attachment]
}

# IAM Role for Fargate
resource "aws_iam_role" "fargate_role" {
  name = "fargate_execution_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Principal = {
          Service = "eks-fargate.amazonaws.com"
        }
      }
    ]
  })
}

# Create the EKS Fargate Profile
resource "aws_eks_fargate_profile" "fargate_profile" {
  cluster_name           = aws_eks_cluster.eks_cluster.name
  fargate_profile_name   = "my-fargate-profile"
  pod_execution_role_arn = aws_iam_role.fargate_role.arn
  subnet_ids             = [
    module.subnet_module.public_subnet_1_id,
    module.subnet_module.public_subnet_2_id
  ]

  selector {
    namespace = "default"
  }

  depends_on = [aws_iam_role_policy_attachment.fargate_role_attachment]
}
