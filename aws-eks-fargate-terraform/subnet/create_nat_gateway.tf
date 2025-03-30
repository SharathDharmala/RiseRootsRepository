########### Elastic IP for NAT Gateway ###########
resource "aws_eip" "nat_gateway_eip" {
  domain = "vpc"  # Using 'domain = "vpc"' instead of 'vpc = true' as it is the correct syntax
}

########## Internet Gateway (Public Subnet) ##########
resource "aws_internet_gateway" "internet_gateway" {
  vpc_id = aws_vpc.main.id  # Ensure this references the VPC you're working with
}

########## NAT Gateway ##########
resource "aws_nat_gateway" "nat_gateway" {
  allocation_id = aws_eip.nat_gateway_eip.id
  subnet_id     = aws_subnet.public_subnet_nat.id  # Reference to the public subnet for NAT Gateway
  
  depends_on = [aws_internet_gateway.internet_gateway]  # Ensure NAT Gateway is created after the Internet Gateway
}

########## Public Subnet for NAT Gateway ##########
resource "aws_subnet" "public_subnet_nat" {
  vpc_id            = aws_vpc.main.id  # Reference to the VPC ID
  cidr_block        = "10.0.0.0/24"    # Adjust as necessary
  availability_zone = "ap-south-1a"     # Specify the correct Availability Zone
  map_public_ip_on_launch = true        # This makes it a public subnet
  tags = {
    Name = "riseroot_public_subnet_nat"
  }
}
