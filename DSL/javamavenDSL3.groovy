job('Java Maven App DSL3') {
    description('Job of a Java application with Maven')
    scm {
        git('https://github.com/UserMl9/jenkinscourse/new/main', 'main') { node ->
            node / gitConfigName('UserMl9')
            node / gitConfigEmail('aprendizajeml9@gmail.com')
        }
    }
    triggers{
	    githubPush()
    }	
    steps {
        maven {
          mavenInstallation('mavenjenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('mavenjenkins')
          goals('test')
        }
        shell('''
          echo "Delivery: Deploying the application" 
          java -jar "/var/jenkins_home/workspace/Java Maven with App DSL3/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}

job('Test Job Hello World'){
    description('Test Job Hello World')
    steps {
        shell('''
            echo "Hello World!!!" 
        ''')  
    }
}
