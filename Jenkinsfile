pipeline {

  environment {
    PROJECT = "coral-id"
    APP_NAME = "coral-id"
    FE_SVC_NAME = "${APP_NAME}-api"
    CLUSTER = "jenkins-cd"
    CLUSTER_ZONE = "asia-southeast2-a"
    IMAGE_TAG = "gcr.io/${PROJECT}/${APP_NAME}:${env.BRANCH_NAME}.${env.BUILD_NUMBER}"
    JENKINS_CRED = "${PROJECT}"
  }

  agent  {
    kubernetes {
        label 'cloud-builders'
        yaml """
        apiVersion: v1
        kind: Pod
        metadata:
        labels:
          component: ci
        spec:
          serviceAccountName: cd-jenkins
          containers:
          - name: gcloud
            image: gcr.io/cloud-builders/gcloud
            command:
            - cat
            tty: true
          - name: kubectl
            image: gcr.io/cloud-builders/kubectl
            command:
            - cat
            tty: true
        """
    }
  }
  stages {
    stage('Build and push image with Container Builder') {
      steps {
        container('gcloud') {
          sh "PYTHONUNBUFFERED=1 gcloud builds submit --timeout=15m -t ${IMAGE_TAG} ."
        }
      }
    }
    stage('Deploy Production') {
      // Production branch
      when { branch 'cloud-prod' }
      steps{
        container('kubectl') {
        // Change deployed image in canary to the one we just built
          step([$class: 'KubernetesEngineBuilder', namespace:'production', projectId: env.PROJECT, clusterName: env.CLUSTER, zone: env.CLUSTER_ZONE, manifestPattern: 'k8s/services', credentialsId: env.JENKINS_CRED, verifyDeployments: false])
          step([$class: 'KubernetesEngineBuilder', namespace:'production', projectId: env.PROJECT, clusterName: env.CLUSTER, zone: env.CLUSTER_ZONE, manifestPattern: 'k8s/production', credentialsId: env.JENKINS_CRED, verifyDeployments: true])
          sh("echo http://`kubectl --namespace=production get service/${FE_SVC_NAME} -o jsonpath='{.status.loadBalancer.ingress[0].ip}'` > ${FE_SVC_NAME}")
        }
      }
    }
    stage('Deploy Dev') {
      // Developer Branches
      when { branch 'cloud-dev'}
      steps {
        container('kubectl') {
          // Create namespace if it doesn't exist
          // sh("kubectl get ns ${env.BRANCH_NAME} || kubectl create ns ${env.BRANCH_NAME}")
          // Don't use public load balancing for development branches
          sh("sed -i.bak 's#gcr.io/cloud-solutions-images/coral-id:1.0.0#${IMAGE_TAG}#' ./k8s/dev/*.yaml")
          sh("kubectl --namespace=${env.BRANCH_NAME} apply -f k8s/dev/")
          echo 'To access your environment run `kubectl proxy`'
          echo "Then access your service via http://localhost:8001/api/v1/proxy/namespaces/${env.BRANCH_NAME}/services/${FE_SVC_NAME}:80/"
        }
      }
    }
  }
}