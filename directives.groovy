pipeline {
    agent any
    environment {
        vstisblr1 = "Building stage"
        variable2 = "Testing Stage"
    }
    stages {
        stage('Build') {
            steps {
                sh 'echo building > build.txt'
                echo "This is ${env.vstisblr1}" // Correct reference to the environment variable
            }
        }
        stage('Test') {
            steps {
                sh 'ls -la'
                echo "This is ${env.variable2}" // Correct reference to the environment variable
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying to prod"
                echo "Job name is ${env.JOB_NAME}" // Correct reference for environment variable
                echo "Job URL is ${env.JOB_URL}" // Corrected spelling: job_url
                echo "Git URL: ${env.GIT_URL}" // Corrected spelling: git_url
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
