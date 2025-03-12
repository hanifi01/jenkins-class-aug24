pipeline {
    agent any

    parameters {
        string(name: 'tf_TEST', description: 'Terraform Action', defaultValue: '') // Fixed syntax for string parameter
        booleanParam(name: 'IS_PROD', description: 'Is this production environment?', defaultValue: true) // Corrected booleanParam syntax
    }

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

        stage('tf-apply') {
            steps {
                dir('infra') {
                    echo "Is this production environment? ${params.IS_PROD}" // Corrected syntax for params usage
                    sh "terraform ${params.tf_TEST} -auto-approve" // Fixed typo and corrected parameter reference
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
                cleanWs() // Clean workspace
                dir("${workspace_tmp}") {
                    deleteDir() // Ensure temporary directories are cleaned up
                }
            }
        }
    }
}
