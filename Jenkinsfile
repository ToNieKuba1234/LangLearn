pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven') {
            stages {
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
        }

        stage('Build Docker Images') {
            steps {
                dir('langlearn-backend') {
                    sh 'chmod +x backend-build.sh && ./backend-build.sh'
                }
                dir('langlearn-frontend') {
                    sh 'chmod +x frontend-build.sh && ./frontend-build.sh'
                }
                dir('langlearn-db') {
                    sh 'chmod +x db-build.sh && ./db-build.sh'
                }
            }
        }


        stage("Minikube") {
            steps {
                script {
                    sh "cp ~/.kube/config /tmp/kubeconfig"
                    
                    withEnv(['KUBECONFIG=/tmp/kubeconfig']) {
                        sh "kubectl config set-cluster minikube --server=https://172.17.0.1:8443 --insecure-skip-tls-verify"
                        
                        sh "kubectl apply -f k8s/deployment.yaml"
                        sh "kubectl apply -f k8s/service.yaml"
                        
                        sh "kubectl rollout status deployment/langlearn"
                    }
                }
            }
        }
    }
}