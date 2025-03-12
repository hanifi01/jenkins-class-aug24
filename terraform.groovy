pipeline {
    agent any

    parameters {
        string(name: 'TF_ACTION', description: 'Enter the Terraform action (e.g., plan, apply, destroy)', defaultValue: 'apply') // Default action to apply
        booleanParam(name: 'IS_PROD', description: 'Is this production environment?', defaultValue: true) // Boolean parameter for production
        choice(name: 'CHOICE_PARAM', description: 'Select a choice:', choices: ['Terraform', 'CloudFormation', 'Pulumi']) // Dropdown choice parameter
    }

    options { 
        buildDiscarder(logRotator(numToKeepStr: '3')) // Keep only the last 3 builds
        retry(2) // Retry the pipeline twice on failure
        timeout(time: 1, unit: 'HOURS') // Timeout for the pipeline
        ansiColor('xterm') // Enable colored output in the logs
    }

    stages {
        stage('tf-init') {
            steps {
                dir('infra') {
                    sh 'terraform init' // Initialize Terraform
                    echo "Selected choice is: ${params.CHOICE_PARAM}" // Log the user's choice
                }
            }
        }

        stage('tf-check') {
            steps {
                dir('infra') {
                    sh 'terraform fmt && terraform validate' // Format and validate Terraform code
                }
            }
        }

        stage('tf-plan') {
            steps {
                dir('infra') {
                    sh 'terraform plan' // Generate and display the Terraform plan
                }
            }
        }

        stage('tf-apply') {
            steps {
                dir('infra') {
                    input message: 'Do you want to approve the deployment?', ok: 'Yes' // Prompt user for approval
                    echo "Initiating deployment..." // Indicate the start of deployment
                    echo "Is this production environment? ${params.IS_PROD}" // Show boolean parameter value
                    echo "Executing Terraform Action: ${params.TF_ACTION}" // Display the action being executed
                    sh "terraform ${params.TF_ACTION} -auto-approve" // Perform the Terraform action
                }
            }
        }
    }

    post {
        success {
            echo 'Infrastructure build successful' // Log success
        }
        failure {
            echo 'Infrastructure build failed' // Log failure
        }
        aborted {
            echo 'Pipeline aborted' // Log if the pipeline was aborted
        }
        always {
            echo 'Pipeline build finished and cleaning up...' // Indicate cleanup
            cleanWs() // Cleanup workspace
        }
    }
}
