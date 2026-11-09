pipeline {
    agent any

    tools {
        jdk 'JDK-25'
        maven 'Maven-3.9.9'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser to test on')
    }

    stages {
        stage('Compile') {
            steps {
                bat 'mvn clean test-compile'
            }
        }
        stage('Test') {
            steps {
                bat "mvn test -Dbrowser=${params.BROWSER} -Dheadless=true"
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/surefire-reports/**', allowEmptyArchive: true
        }
    }
}