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
        ansiColor('xterm') // Enable colored output in the log
    }

    stages {
        stage('tf-init') {
            steps {
                dir('infra') {
                    sh 'terraform init' // Initialize Terraform
                    echo "Selected choice is: ${params.CHOICE_PARAM}" // Display the user's choice
                }
            }
        }

        stage('tf-check') {
            steps {
                dir('infra') {
                    sh 'terraform fmt && terraform validate' // Format and validate the Terraform configuration
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
                    echo "Initiating deployment..." // Indicate start of deployment
                    echo "Is this production environment? ${params.IS_PROD}" // Show boolean parameter value
                    echo "Executing Terraform Action: ${params.TF_ACTION}" // Show the action being executed
                    sh "terraform ${params.TF_ACTION} -auto-approve" // Execute the Terraform action
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
            echo 'Pipeline build finished and cleaning up...' // Indicate pipeline cleanup
            cleanWs() // Cleanup workspace
        }
    }
}
