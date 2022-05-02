node('maven') {
stages {
            stage("Clone Source") {
              steps {
                git url: "https://github.com/azza1998/notes-app.git", branch: "master"}
                   }
            stage ("sonarqube analysis"){
                nodejs(nodeJSInstallationName: 'nodejs'){
                    sh "npm install"
                    withSonarQubeEnv('SQ'){
                        sh "npm install sonar-scanner"
                        sh "npm run SQ"
                    }
                }
            }

            stage("create app"){
                steps{
                    sh "cd notes-app "
                    sh "oc apply -f openshift-specifications/ -n note-app"
                    sh "oc apply -f openshift-specifications/with-dockerfile -n note-app"
                    }
                }
            stage ("build & deploy"){
                openshiftBuild(buildConfig: 'notes-app', showBuildLogs: 'true', namespace: 'note-app')

            }
            stage ("verify deploy"){
                openshiftVerifyDeployment(namespace: 'note-app', depCfg: 'notes-app', replicaCount:'1', verifyReplicaCount: 'true', waitTime: '180000')

            }
            }
    }