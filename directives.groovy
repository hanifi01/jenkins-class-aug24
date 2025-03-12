pipeline {
    agent any
    environment {
        vstisblr1 = "Building stage"
        variable2 = "Testing Stage"
        jenkinsprivate = credentials('jenkins-private-key') // Correct handling of credentials
    }
    parameters {
        string(name: 'tf_TEST', description: 'Terraform Action', defaultValue: '') // Fixed syntax for parameter definition
    }
    stages {
        stage('Info') {
            steps {
                echo "Current DevOps Batch in Akumo is ${params.tf_TEST}" // Corrected parameter reference
            }
        }
        stage('Build') {
            steps {
                sh 'echo building > build.txt'
                echo "This is ${env.vstisblr1}" // Correctly referencing environment variable
            }
        }
        stage('Test') {
            steps {
                sh 'ls -la'
                echo "This is ${env.variable2}" // Correctly referencing environment variable
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying to prod"
                echo "Job name is ${env.JOB_NAME}" // Correctly referencing environment variable
                echo "Job URL is ${env.JOB_URL}" // Corrected variable name
                echo "Git URL: ${env.GIT_URL}" // Corrected variable name
            }
        }
    }
    post {
        success {
            echo 'The pipeline succeeded!'
        }
        failure {
            echo 'The pipeline failed :('
        }
    }
}
