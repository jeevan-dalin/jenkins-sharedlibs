//Jenkins pipeline script to use the shared library and deploy VM in GCP.
@Library('jenkins-sharedlibs@main') _

pipeline {
    agent any

    stages {
        stage('Initialize') {
            steps {
                echo "Starting pipeline..."
            }
        }

        stage('GCP Deployment') {
            steps {
                script {
                    def vmName = "ps-test-sharedlib-vm"
                    def gcpProject = "ps-training"
                    def gcpZone = "us-central1-a"
                    def machineType = "e2-medium"

                    withCredentials([file(credentialsId: 'gcp-service-account-key', variable: 'GCP_KEY')]) {
                        sh "export GOOGLE_APPLICATION_CREDENTIALS=\"\$GCP_KEY\""
                        // calling shared lib.
                        create_vm.call(vmName, gcpProject, gcpZone, machineType)
                    }
                }
            }
        }
    }
}
