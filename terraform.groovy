// pipeline{
//     agent any

//     options{
//       buildDiscarder(logRotator(numToKeepStr: '3'))
//       retry(2)
//       timeout(time: 1, unit: 'MINUTES')
//       ansiColor('xterm')
//     }

//     parameters{
//         // string(name: 'TF_ACTION', description: "Terraform action", defaultValue: '')
//         booleanParam(name: 'IS_PROD', description: 'Environment:', defaultValue: true)
//         choice(name: 'CHOICE_PARAM', description: 'Select a choice:', choices: ['Terraform', 'Cloudformation', 'Plumi'])
//         choice(name: 'TF_ACTION', description: 'Select Terraform action:', choices: ['apply', 'destroy'])
//     }

//     stages{
//         stage('init'){
//           steps{
//                 dir('infra'){
//                     sh 'terraform init'
//                     echo "Selected choice is: ${params.CHOICE_PARAM}"
//                 }
//           }
//         }
//         stage('tf-check'){
//           steps{
//                 dir('infra'){
//                     sh 'terraform fmt; terraform validate'
//                 }
//           }
//         }
//         stage('plan'){
//           steps{
//                 dir('infra'){
//                     sh 'terraform plan'
//                 }
//               }
//           }
        
//         stage('execute'){
//           steps{
//                 dir('infra'){
//                     echo "Is this Production Environment? ${params.IS_PROD}"
//                     input message: 'Do you want to approve the deployment?', ok: 'Yes'
//                     sh "terraform ${params.TF_ACTION} -auto-approve"
//                 }
//             }
//         }
//     }
//     post {
//     success {
//         echo 'Infra has been build successfully'
//     }
//     failure {
//         echo 'Infra build failed'
//     }
//     aborted {
//         echo 'Pipeline aborted!'
//     }
//     cleanup { // always executes
//         echo 'Pipeline build finished and cleaning up .....'
//         cleanWs()
//         dir("${workspace_tmp}") {
//             deleteDir()
//         }
//     }
// }
// }





