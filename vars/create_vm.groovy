def call(String vmName, String gcpProject, String gcpZone, String machineType) {

    stage('Deploy VM to GCP') {
        echo "Starting deployment of VM: ${vmName} in project: ${gcpProject}"
        try {
            def vmExists = sh(
                script: "gcloud compute instances list --filter=name='${vmName}' --project='${gcpProject}' --zones='${gcpZone}' --format='value(name)'",
                returnStdout: true
            ).trim()

            if (vmExists) {
                echo "VM '${vmName}' already exists. Skipping creation."
            } else {
                sh """
                gcloud compute instances create ${vmName} \
                --project=${gcpProject} \
                --zone=${gcpZone} \
                --machine-type=${machineType} \
                --network-interface=network-name=default \
                --boot-disk-size=10GB \
                --boot-disk-type=pd-standard \
                --boot-disk-device-name=${vmName}
                """
                echo "Successfully created VM: ${vmName}"
            }
        } catch (Exception e) {
            error "Failed to deploy VM: ${e.message}"
        }
    }
}
