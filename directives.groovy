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
                echo "This is ${vstisblr1}" // Correct reference to environment variable
            }
        }
        stage('Test') {
            steps {
                sh 'ls -la'
                echo "This is ${variable2}" // Correct reference to environment variable
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo deploying to prod'
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
