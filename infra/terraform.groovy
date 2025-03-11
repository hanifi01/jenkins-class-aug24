// pipeline {
//     agent any

//     stages {
//         stage('Terraform Init') {
//             steps {
//                 dir('.infra') {  // Ensure .infra has your .tf files
//                     script {
//                         // Initialize Terraform in the .infra directory
//                         echo 'Initializing Terraform...'
//                         sh 'terraform init'
//                     }
//                 }
//             }
//         }

//         stage('Terraform Validate') {
//             steps {
//                 dir('.infra') {  // Ensure .infra has your .tf files
//                     script {
//                         // Validate Terraform configuration
//                         echo 'Validating Terraform configuration...'
//                         sh 'terraform validate'
//                     }
//                 }
//             }
//         }

//         stage('Terraform Format') {
//             steps {
//                 dir('.infra') {  // Ensure .infra has your .tf files
//                     script {
//                         // Check if Terraform configuration files are formatted correctly
//                         echo 'Checking Terraform format...'
//                         sh 'terraform fmt -check'
//                     }
//                 }
//             }
//         }

//         stage('Terraform Plan') {
//             steps {
//                 dir('.infra') {  // Ensure .infra has your .tf files
//                     script {
//                         // Generate and show Terraform execution plan
//                         echo 'Running Terraform plan...'
//                         sh 'terraform plan'
//                     }
//                 }
//             }
//         }

//         stage('Terraform Apply') {
//             steps {
//                 dir('.infra') {  // Ensure .infra has your .tf files
//                     script {
//                         // Apply Terraform changes automatically with the -auto-approve flag
//                         echo 'Applying Terraform changes...'
//                         sh 'terraform apply -auto-approve'
//                     }
//                 }
//             }
//         }
//     }

//     post {
//         success {
//             echo 'Terraform pipeline executed successfully!'
//         }
//         failure {
//             echo 'Terraform pipeline failed.'
//         }
//     }
// }
