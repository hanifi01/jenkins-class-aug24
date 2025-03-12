pipeline {
    agent any

    parameters {
        string(name: 'TF_ACTION', description: 'Enter the Terraform action (e.g., plan, apply, destroy)', defaultValue: 'apply') // Ensure action defaults to apply
        booleanParam(name: 'IS_PROD', description: 'Is this production environment?', defaultValue: true) // Properly configured boolean parameter
        choice(name: 'CHOICE_PARAM', description: 'Select a choice:', choices: ['Terraform', 'CloudFormation', 'Pulumi']) // Correct syntax for choice parameter
    }

    options { 
        buildDiscarder(logRotator(numToKeepStr: '3')) // Keep only the last 3 builds
        retry(2) // Retry the pipeline twice on failure
        timeout(time: 1, unit: 'HOURS') // Set a timeout for the pipeline
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
                    echo "Executing Terraform Action: ${params.TF_ACTION}" // Debugging to show the value
                    sh "terraform ${params.TF_ACTION} -auto-approve" // Correctly include TF_ACTION value
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
