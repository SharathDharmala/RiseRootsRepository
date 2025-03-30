resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.main.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type = "fixed-response"
    fixed_response {
      status_code  = 200
      content_type = "text/plain"
      message_body = "OK"
    }
  }
}

resource "aws_lb_listener_rule" "eureka_rule" {
  listener_arn = aws_lb_listener.http.arn
  priority     = 10

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.eureka_target_group.arn
  }

  condition {
    path_pattern {
      values = ["/eureka*"]
    }
  }
}

resource "aws_lb_listener_rule" "cronservice_rule" {
  listener_arn = aws_lb_listener.http.arn
  priority     = 20

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.cronservice_target_group.arn
  }

  condition {
    path_pattern {
      values = ["/cronservice*"]
    }
  }
}
