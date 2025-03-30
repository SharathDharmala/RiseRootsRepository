resource "aws_iam_user_policy_attachment" "eks_cluster_policy" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"  # This policy allows the creation and management of EKS clusters
}

resource "aws_iam_user_policy_attachment" "eks_service_policy" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSServicePolicy"  # This policy provides service permissions
}

resource "aws_iam_user_policy_attachment" "eks_worker_node_policy" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"  # This policy provides worker node permissions
}

resource "aws_iam_user_policy_attachment" "vpc_full_access" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/AmazonVPCFullAccess"
}

resource "aws_iam_user_policy_attachment" "iam_pass_role" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/IAMFullAccess"
}

resource "aws_iam_user_policy_attachment" "iam_read_only_access" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/IAMReadOnlyAccess"
}

resource "aws_iam_user_policy_attachment" "admin_access" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
}

resource "aws_iam_user_policy_attachment" "iam_attach_policy_permission" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/IAMFullAccess"
}

resource "aws_iam_user_policy_attachment" "eks_create_cluster_permission" {
  user       = "SharathDharmala"
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"  # This policy is required for creating the EKS cluster
}
resource "aws_iam_role_policy_attachment" "eks_cluster_policy_attachment" {
  role       = aws_iam_role.eks_cluster_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
}

resource "aws_iam_role_policy_attachment" "eks_vpc_policy_attachment" {
  role       = aws_iam_role.eks_cluster_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSVPCResourceController"
}
resource "aws_iam_role_policy_attachment" "fargate_role_attachment" {
  role       = aws_iam_role.fargate_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_FargatePodExecutionRolePolicy"
}
