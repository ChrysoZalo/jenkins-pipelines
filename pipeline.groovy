pipeline {
    agent any
    // tools {
    //     // Install the Maven version configured as "M3" and add it to the path.
    //     maven "M3"
    // }

    triggers { pollSCM('* * * * *') }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/ChrysoZalo/jgsu-spring-petclinic.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                //sh './mvnw clean package'
                sh 'false'
            }

            post {
                always {
                    //junit '**/target/surefire-reports/TEST-*.xml'
                    //archiveArtifacts 'target/*.jar'
                // }

                // changed {
                    emailext attachLog: true, 
                    body: 'Please go to ${BUILD_URL} and verify the build', 
                    compressLog: true, 
                    recipientProviders: [
                        upstreamDevelopers(), 
                        requestor()
                    ],
                    to: 'test@jenkins', 
                    subject: "Job \'${JOB_NAME}\' (build ${BUILD_NUMBER}) ${currentBuild.result}"
                }
            }
        }
    }
}
