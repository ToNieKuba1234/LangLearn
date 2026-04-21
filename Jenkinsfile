pipeline {
    agent any

    environment {
        NEXUS_HOST_IP = "192.168.53.53" 
        NEXUS_PORT = "8081"
        NEXUS_REGISTRY = "${NEXUS_HOST_IP}:${NEXUS_PORT}"
        REPO_PATH = "docker-hosted"
    }

    stages {
        stage('Compile') {
            steps {
                dir('langlearn-backend') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('langlearn-backend') {
                    sh 'mvn test'
                }
            }
        }

        stage('Build JAR') {
            steps {
                dir('langlearn-backend') {
                    sh 'mvn package -DskipTests'
                }
            }
        }

        stage('Build & Push to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    script {
                        sh "echo '${PASS}' | docker login ${NEXUS_REGISTRY} -u ${USER} --password-stdin"

                        dir('langlearn-backend') {
                            sh "docker build -t langlearn-backend:latest ."
                            sh "docker tag langlearn-backend:latest ${NEXUS_REGISTRY}/${REPO_PATH}/langlearn-backend:latest"
                            sh "docker push ${NEXUS_REGISTRY}/${REPO_PATH}/langlearn-backend:latest"
                        }
                    }
                }
            }
        }

        stage("Deploy to Minikube") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    script {
                        sh "cp ~/.kube/config /tmp/kubeconfig"
                        withEnv(['KUBECONFIG=/tmp/kubeconfig']) {
                            sh """
                            kubectl delete secret nexus-registry --ignore-not-found
                            kubectl create secret docker-registry nexus-registry \
                              --docker-server=${NEXUS_REGISTRY} \
                              --docker-username=${USER} \
                              --docker-password=${PASS}
                            """
                            
                            sh "cp k8s/deployment.yaml k8s/deployment-temp.yaml"
                            sh "sed -i 's|image: langlearn-backend:latest|image: ${NEXUS_REGISTRY}/${REPO_PATH}/langlearn-backend:latest|g' k8s/deployment-temp.yaml"
                            
                            sh "kubectl apply -f k8s/deployment-temp.yaml --validate=false"
                            sh "kubectl apply -f k8s/service.yaml --validate=false"
                            sh "rm k8s/deployment-temp.yaml"
                            
                            sh "kubectl rollout status deployment/langlearn-backend-dep"
                        }
                    }
                }
            }
        }
    }
}