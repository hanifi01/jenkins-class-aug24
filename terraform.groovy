pipeline {
    agent any

    stages {
        stage('tf-init') {
            steps {
                dir('infra') {
                    sh 'terraform init'
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
                    sh 'terraform apply -auto-approve'
                }
            }
        }
    }

    post {
        success {
            echo 'Infra build successful'
        }
        failure {
            echo 'Infra build failed'
        }
        aborted {
            echo 'Pipeline aborted'
        }
        always {
            steps {
                echo 'Pipeline build finished and cleaning up...'
                cleanWs()
                // Remove temporary directories or files explicitly if required
                dir("${workspace_tmp}") {
                    deleteDir()
                }
            }
        }
    }
}
