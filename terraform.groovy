pipeline {
    agent any

    parameters {
        string(name: 'tf_TEST', description: 'Terraform Action', defaultValue: '') // Fixed syntax for string parameter
        booleanParam(name: 'IS_PROD', description: 'Is this production environment?', defaultValue: true) // Corrected booleanParam syntax
        choice(name: 'CHOICE_PARAM', description: 'Select a choice:', choices: ['Terraform', 'Cloudformation', 'Pulumi']) // Fixed choice syntax
    }

    stages {
        stage('tf-init') {
            steps {
                dir('infra') {
                    sh 'terraform init'
                    echo "Selected choice is: ${params.CHOICE_PARAM}" // Corrected syntax for parameter reference
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
                    sh 'terraform plan' // Corrected action to 'plan' for tf-plan stage
                }
            }
        }

        stage('tf-apply') {
            steps {
                dir('infra') {
                    echo "Is this production environment? ${params.IS_PROD}" // Corrected syntax for params usage
                    sh "terraform ${params.tf_TEST} -auto-approve" // Corrected parameter reference
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
