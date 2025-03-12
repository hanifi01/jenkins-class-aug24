pipeline {
    agent any
    environment {
        vstisblr1 = "Building stage"
        variable2 = "Testing Stage"
        jenkinsprivate = credentials('jenkins-private-key') // Removed extra spaces in the credentials call
    }
    parameters {
        string(name: 'BATCH', description: 'Akumo DevOps Batch', defaultValue: '') // Corrected syntax and fixed typos
    }
    stages {
        stage('Info') {
            steps {
                echo "Current DevOps Batch in Akumo is ${params.BATCH}" // Corrected params reference
            }
        }
        stage('Build') {
            steps {
                sh 'echo building > build.txt'
                echo "This is ${env.vstisblr1}" // Correct reference to environment variable
            }
        }
        stage('Test') {
            steps {
                sh 'ls -la'
                echo "This is ${env.variable2}" // Correct reference to environment variable
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying to prod"
                echo "Job name is ${env.JOB_NAME}" // Correct reference for environment variable
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
