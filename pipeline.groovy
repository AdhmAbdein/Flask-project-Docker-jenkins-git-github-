pipeline {
    
    agent {label 'node1'}
    
    environment {
        docker_img = 'adhmabdein/myflaskimg'
    }
    
    stages {
        
        stage('pull code from github'){
            steps{
                script{
                       git "https://github.com/AdhmAbdein/flask-project.git"
                       //git switch master
                }
            }
        }  
        stage('docker hub log in'){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId:'docker_hub' , usernameVariable:'d_hub_usr' , passwordVariable:'d_hub_pass')]){
                        sh 'docker login -u ${d_hub_usr} -p ${d_hub_pass}'                
                    }
                }
            }
        }
        stage('build docker image'){
            steps{
                script{
                    sh 'docker build -t ${docker_img} -f Dockerfile .'
                    sh 'docker push ${docker_img}'
                }
            }
        }
        stage('CD'){
            steps{
                script{
                    sh 'docker run -it -d --name flask_cont -p 5000:5000 ${docker_img}'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    sh 'docker exec flask_cont bash -c "export PYTHONPATH=/app:$PYTHONPATH && pytest /app/tests/test_app.py"'
                }
            }
        }
    }
}