pipeline {
    agent any

    tools {
        maven 'Maven 3.9' 
        jdk 'Java 25'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('maven') {
            stage('Compile') {
                steps {
                    sh 'mvn clean compile'
                }
            }

            stage('Run Tests') {
                steps {
                    sh 'mvn test'
                }
                post {
                    always {
                        junit '**/target/surefire-reports/*.xml'
                    }
                }
            }

            stage('Build JAR') {
                steps {
                    sh 'mvn package -DskipTests'
                }
            }
        }

        stage('build docker images') {
            
        }

        stage("minikube") {
            steps {
                script {
                    sh "kubectl config set-cluster minikube --server=https://172.17.0.1:8443"
                    
                    sh "kubectl apply -f k8s/deployment.yaml"
                    sh "kubectl apply -f k8s/service.yaml"
                    
                    sh "kubectl rollout status deployment/langlearn"
                }
            }
        }
    }
}