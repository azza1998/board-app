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
            stage ("Sonarqube Analysis"){
                steps{
                    def sonarHome =  tool 'SQ';
                    sonarIP = getIP('sonarqube.sonarqube.svc.cluster.local.')
                    sh "${sonarHome}/bin/sonar-scanner -Dsonar.projectKey=Notes-app -Dsonar.projectName=Notes-app -Dsonar.host.url=http://${sonarIP}:9000 -Dsonar.login=admin -Dsonar.password=azza"
                    }}
            stage("Create App"){
                steps{
                
                    sh "oc apply -f openshift-specifications/ -n notes-app"
                    sh "oc apply -f openshift-specifications/with-dockerfile -n notes-app"
                    }
                }
            stage ("Build & Push image to Nexus"){
                steps{
                openshiftBuild(bldCfg: 'notes-app', showBuildLogs: 'true', namespace: 'notes-app')

            }}
            stage ("Deploy App"){
                steps{
                openshiftVerifyDeployment(namespace: 'notes-app', depCfg: 'notes-app', replicaCount:'1', verifyReplicaCount: 'true', waitTime: '180000')

            }}
            
    
    
}
    
}
