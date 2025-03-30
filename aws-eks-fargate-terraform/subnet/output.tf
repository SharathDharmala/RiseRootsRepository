output "eks_cluster_endpoint" {
  description = "The endpoint URL for the EKS cluster"
  value       = aws_eks_cluster.eks_cluster.endpoint
}
