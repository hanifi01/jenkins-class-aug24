pipeline {
    agent any

    parameters {
        string(name: 'TF_ACTION', description: 'Enter the Terraform action (e.g., plan, apply)', defaultValue: '') // Correct syntax for string parameter
        booleanParam(name: 'IS_PROD', description: 'Is this production environment?', defaultValue: true) // Properly configured boolean parameter
        choice(name: 'CHOICE_PARAM', description: 'Select a choice:', choices: ['Terraform', 'CloudFormation', 'Pulumi']) // Correct syntax for choice parameter
        choice(name: 'TF_SECOND_ACTION', description: 'Select a Terraform action:', choices: ['apply', 'destroy']) // Properly named and formatted choice parameter
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
                    sh 'terraform plan' // Use 'plan' action for this stage
                }
            }
        }

        stage('tf-apply') {
            steps {
                dir('infra') {
                    echo "Is this production environment? ${params.IS_PROD}" // Display the value of the boolean parameter
                    echo "Selected second action is: ${params.TF_SECOND_ACTION}" // Display the selected second action
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
            echo 'Pipeline build finished and cleaning up...'
            cleanWs() // Clean the workspace
        }
    }
}
