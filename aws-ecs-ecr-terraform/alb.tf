resource "aws_lb" "main" {
  name               = "riseroots-app-lb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = var.security_group_ids
  subnets            = var.subnet_ids

  enable_deletion_protection = false
  idle_timeout               = 60

  tags = {
    Name = "riseroots-app-lb"
  }
}
