pipeline{
  agent{
    label "master" }
 stages {
            stage("Clone Source") {
              steps {
             
                git url: "https://github.com/azza1998/Notes-apps.git", branch: "main"
              }
            }
            stage ("sonarqube analysis"){
                steps{
                    def sonarHome =  tool 'SQ'
                    sonarIP = getIP('sonarqube.sonarqube.svc.cluster.local.')
                    sh "${sonarHome}/bin/sonar-scanner -Dsonar.projectKey=Notes-app -Dsonar.projectName=Notes-app -Dsonar.host.url=http://${sonarIP}:9000 -Dsonar.login=admin -Dsonar.password=azza"
    
    
     }}}
     // Get A Service Cluster IP
     def getIP(String lookup){
    sh "getent hosts ${lookup} | cut -f 1 -d \" \" > ipaddress"
    ipaddress = readFile 'ipaddress'
    ipaddress = ipaddress.trim()
    sh 'rm ipaddress'
    return ipaddress
    
}
