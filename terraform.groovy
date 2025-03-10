pipeline {
    agent any

    stages {
        stage('Terraform Init') {
            steps {
                dir('.infra') {  // Ensure .infra has your .tf files
                    script {
                        echo 'Initializing Terraform...'
                        def initOutput = sh(script: 'terraform init', returnStdout: true).trim()
                        echo "Terraform Init Output: ${initOutput}"
                    }
                }
            }
        }

        stage('Terraform Validate') {
            steps {
                dir('.infra') {  // Ensure .infra has your .tf files
                    script {
                        echo 'Validating Terraform configuration...'
                        def validateOutput = sh(script: 'terraform validate', returnStdout: true).trim()
                        echo "Terraform Validate Output: ${validateOutput}"
                    }
                }
            }
        }

        stage('Terraform Format') {
            steps {
                dir('.infra') {  // Ensure .infra has your .tf files
                    script {
                        echo 'Checking Terraform format...'
                        def fmtOutput = sh(script: 'terraform fmt -check', returnStdout: true).trim()
                        echo "Terraform Format Output: ${fmtOutput}"
                    }
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                dir('.infra') {  // Ensure .infra has your .tf files
                    script {
                        echo 'Running Terraform plan...'
                        def planOutput = sh(script: 'terraform plan', returnStdout: true).trim()
                        echo "Terraform Plan Output: ${planOutput}"
                    }
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                dir('.infra') {  // Ensure .infra has your .tf files
                    script {
                        echo 'Applying Terraform changes...'
                        def applyOutput = sh(script: 'terraform apply -auto-approve', returnStdout: true).trim()
                        echo "Terraform Apply Output: ${applyOutput}"
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
