pipeline {
    agent any

    parameters {
        string(name: 'TF_ACTION', description: 'Enter the Terraform action (e.g., plan, apply)', defaultValue: '') // Correct syntax for string parameter
        booleanParam(name: 'IS_PROD', description: 'Is this production environment?', defaultValue: true) // Properly configured boolean parameter
        choice(name: 'CHOICE_PARAM', description: 'Select a choice:', choices: ['Terraform', 'CloudFormation', 'Pulumi']) // Correct syntax for choice parameter
    }

    stages {
        stage('tf-init') {
            steps {
                dir('infra') {
                    sh 'terraform init'
                    echo "Selected choice is: ${params.CHOICE_PARAM}" // Display the selected option in the pipeline log
                }
            }
        }

        stage('tf-check') {
            steps {
                dir('infra') {
                    sh 'terraform fmt && terraform validate'
                }
            }
        }

        stage('tf-plan') {
            steps {
                dir('infra') {
                    sh 'terraform plan' // Updated to 'terraform plan' for this stage
                }
            }
        }

        stage('tf-apply') {
            steps {
                dir('infra') {
                    echo "Is this production environment? ${params.IS_PROD}" // Display the value of the boolean parameter
                    sh "terraform ${params.TF_ACTION} -auto-approve" // Use the string parameter for the Terraform action
                }
            }
        }
    }

    post {
        success {
            echo 'Infrastructure build successful'
        }
        failure {
            echo 'Infrastructure build failed'
        }
        aborted {
            echo 'Pipeline aborted'
        }
        always {
            steps {
                echo 'Pipeline build finished and cleaning up...'
                cleanWs() // Clean the workspace
            }
        }
    }
}
