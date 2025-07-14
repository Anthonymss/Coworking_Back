data "aws_vpc" "default" {
  default = true
}

resource "aws_security_group" "sg" {
  name        = "terraform-sg"
  description = "Allow HTTP, HTTPS, SSH, 8080, 8761"
  vpc_id      = data.aws_vpc.default.id

  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "HTTP"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "HTTPS"
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "App 8080"
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "Eureka 8761"
    from_port   = 8761
    to_port     = 8761
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_iam_role" "ec2_ssm_role" {
  name = "coworking-ec2-ssm-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Principal = {
        Service = "ec2.amazonaws.com"
      },
      Action = "sts:AssumeRole"
    }]
  })
}

resource "aws_iam_role_policy" "ssm_access_policy" {
  name = "ssm-access-policy"
  role = aws_iam_role.ec2_ssm_role.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Action = [
        "ssm:GetParameter"
      ],
      Resource = "*"
    }]
  })
}

resource "aws_iam_instance_profile" "ec2_ssm_profile" {
  name = "coworking-ec2-instance-profile"
  role = aws_iam_role.ec2_ssm_role.name
}

resource "aws_instance" "coworking_ec2" {
  ami                    = "ami-06971c49acd687c30"
  instance_type          = "t3.large"
  key_name               = "aws_ssh_key"
  iam_instance_profile   = aws_iam_instance_profile.ec2_ssm_profile.name

  root_block_device {
    volume_size = 12
    volume_type = "gp3"
  }

  vpc_security_group_ids = [aws_security_group.sg.id]

  tags = {
    Name = "coworking-t3Large-Back"
  }

  user_data = <<-EOF
    #!/bin/bash
    exec > /var/log/user-data.log 2>&1
    set -e

    timedatectl set-timezone America/Lima
    dnf update -y
    dnf install -y git docker nginx python3-certbot-nginx

    systemctl start docker
    systemctl enable docker
    systemctl start nginx
    systemctl enable nginx
    usermod -aG docker ec2-user

    mkdir -p /usr/local/libexec/docker/cli-plugins
    curl -L "https://github.com/docker/compose/releases/download/v2.27.0/docker-compose-linux-x86_64" \
      -o /usr/local/libexec/docker/cli-plugins/docker-compose
    chmod +x /usr/local/libexec/docker/cli-plugins/docker-compose

    # Configuración temporal HTTP para certbot
    cat <<EOL > /etc/nginx/conf.d/coworking.conf
server {
    listen 80;
    server_name coworkingwebsite.website www.coworkingwebsite.website;

    location / {
        proxy_pass http://localhost:8080;
        proxy_http_version 1.1;
        proxy_set_header Upgrade \$http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host \$host;
        proxy_cache_bypass \$http_upgrade;
    }
}
EOL

    nginx -t && systemctl reload nginx

    # Solicitar certificado HTTPS
    certbot --nginx --redirect -d coworkingwebsite.website -d www.coworkingwebsite.website \
      --non-interactive --agree-tos -m giananthonys59@gmail.com || true

    # Renovación automática
    echo "0 3 * * * root certbot renew --quiet" >> /etc/crontab

    # Script de despliegue como ec2-user
    cat <<'EOC' > /home/ec2-user/start-app.sh
#!/bin/bash
cd /home/ec2-user
git clone https://github.com/Anthonymss/Coworking_Back.git
cd Coworking_Back/services
aws ssm get-parameter --name /coworking/env --with-decryption --region us-east-2 --output text --query 'Parameter.Value' > .env
docker compose --env-file .env up -d
EOC

    chown ec2-user:ec2-user /home/ec2-user/start-app.sh
    chmod +x /home/ec2-user/start-app.sh
    su - ec2-user -c "/home/ec2-user/start-app.sh"
  EOF
}

resource "aws_eip_association" "coworking_eip_assoc" {
  instance_id   = aws_instance.coworking_ec2.id
  allocation_id = "eipalloc-0268603e62e9cb93c"
}
