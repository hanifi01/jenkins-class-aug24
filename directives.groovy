pipeline {
    agent any
    environment {
        vstisblr1 = "Building stage"
    }
    stages {
        stage('Build') {
            steps {
                sh 'echo building > build.txt'
                echo "This is ${env.vstisblr1}" // Using the defined environment variable
            }
        }
        stage('Test') {
            steps {
                sh 'ls -la'
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
