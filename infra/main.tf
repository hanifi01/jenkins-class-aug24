# provider "aws" {
#   region = "us-west-2"
# }

# resource "aws_vpc" "default" {
#   cidr_block = "10.0.0.0/16"
# }

# resource "aws_subnet" "default" {
#   vpc_id            = aws_vpc.default.id
#   cidr_block        = "10.0.1.0/24"
#   availability_zone = "us-west-2a"
# }

# resource "aws_instance" "web" {
#   ami           = "ami-00c257e12d6828491"
#   instance_type = "t2.micro"

#   subnet_id = aws_subnet.default.id

#   tags = {
#     Name = "ec2-created-by-jenkins"
#   }
# }
