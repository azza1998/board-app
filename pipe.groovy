def version, mvnCmd = "mvn -s nexus.xml"
pipeline{
  agent{
    label "master"
  }
 stages {
            stage("Clone Source") {
              steps {
             
                git url: "https://github.com/azza1998/Notes-apps.git", branch: "main"
                script {
                  def pom = readMavenPom file: 'pom.xml'
                  version = pom.version
              }
              }
            }
            stage("Archive App") {
            steps {
              sh "${mvnCmd} deploy -DskipTests=true -P nexus3"
            }
          }
 }
}
