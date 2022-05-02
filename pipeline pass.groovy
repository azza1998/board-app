pipeline{
  agent{
    label "master"
  }
 stages {
            stage("Clone Source") {
              steps {
             
                git url: "https://github.com/azza1998/Notes-apps.git", branch: "main"
              }
            }
            stage("create app"){
                steps{
                
                    sh "oc apply -f openshift-specifications/ -n note-app"
                    sh "oc apply -f openshift-specifications/with-dockerfile -n note-app"
                    }
                }
            stage ("build & deploy"){
                steps{
                openshiftBuild(bldCfg: 'notes-app', showBuildLogs: 'true', namespace: 'note-app')

            }}
            stage ("verify deploy"){
                steps{
                openshiftVerifyDeployment(namespace: 'note-app', depCfg: 'notes-app', replicaCount:'1', verifyReplicaCount: 'true', waitTime: '180000')

            }}
            
    
    
}
    
}