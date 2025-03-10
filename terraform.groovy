pipeline {
    agent any

    stages {
        stage('Terraform Init') {
            steps {
                script {
                    // Initialize Terraform (adjust the directory if needed)
                    dir('path/to/your/terraform/files') {
                        sh 'terraform init'
                    }
                }
            }
        }

        stage('Terraform Validate') {
            steps {
                script {
                    // Validate Terraform configuration
                    dir('path/to/your/terraform/files') {
                        sh 'terraform validate'
                    }
                }
            }
        }

        stage('Terraform Format') {
            steps {
                script {
                    // Format Terraform configuration files
                    dir('path/to/your/terraform/files') {
                        sh 'terraform fmt -check'
                    }
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    // Generate and show Terraform execution plan
                    dir('path/to/your/terraform/files') {
                        sh 'terraform plan'
                    }
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    // Apply Terraform changes automatically
                    dir('path/to/your/terraform/files') {
                        sh 'terraform apply -auto-approve'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Terraform pipeline executed successfully!'
        }
        failure {
            echo 'Terraform pipeline failed.'
        }
    }
}
