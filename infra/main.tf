resource "aws_instance" "web" {
  ami           = "ami-00c257e12d6828491"
  instance_type = "t2.micro"

  tags = {
    Name = "ec2-created-by-jenkins"
  }
}
 