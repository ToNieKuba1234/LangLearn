pipeline {
    agent any

    environment {
        NEXUS_HOST_IP = "192.168.53.53" 
        NEXUS_PORT = "8081"
        NEXUS_REGISTRY = "${NEXUS_HOST_IP}:${NEXUS_PORT}"
    }

    stages {
        stage('Build & Push to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    script {
                        sh "docker login ${NEXUS_REGISTRY} -u ${USER} -p ${PASS}"

                        dir('langlearn-backend') {
                            sh 'chmod +x backend-build.sh && ./backend-build.sh'
                            sh "docker tag langlearn-backend:latest ${NEXUS_REGISTRY}/langlearn-backend:latest"
                            sh "docker push ${NEXUS_REGISTRY}/langlearn-backend:latest"
                        }
                        dir('langlearn-frontend') {
                            sh 'chmod +x frontend-build.sh && ./frontend-build.sh'
                            sh "docker tag langlearn-frontend:latest ${NEXUS_REGISTRY}/langlearn-frontend:latest"
                            sh "docker push ${NEXUS_REGISTRY}/langlearn-frontend:latest"
                        }
                        dir('langlearn-db') {
                            sh 'chmod +x db-build.sh && ./db-build.sh'
                            sh "docker tag langlearn-db:latest ${NEXUS_REGISTRY}/langlearn-db:latest"
                            sh "docker push ${NEXUS_REGISTRY}/langlearn-db:latest"
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
                            sh "sed -i 's|image: langlearn-backend:latest|image: ${NEXUS_REGISTRY}/langlearn-backend:latest|g' k8s/deployment-temp.yaml"
                            sh "sed -i 's|image: langlearn-frontend:latest|image: ${NEXUS_REGISTRY}/langlearn-frontend:latest|g' k8s/deployment-temp.yaml"
                            sh "sed -i 's|image: langlearn-db:latest|image: ${NEXUS_REGISTRY}/langlearn-db:latest|g' k8s/deployment-temp.yaml"

                            sh "kubectl apply -f k8s/deployment-temp.yaml --validate=false"
                            sh "kubectl apply -f k8s/service.yaml --validate=false"
                            sh "rm k8s/deployment-temp.yaml"
                        }
                    }
                }
            }
        }
    }
}