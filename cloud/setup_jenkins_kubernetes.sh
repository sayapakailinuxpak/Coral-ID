# This script based on https://github.com/GoogleCloudPlatform/continuous-deployment-on-kubernetes

###############################
####### Initial Setup #########
###############################

# Set up default compute zone
gcloud config set compute/zone us-east1-d

# Create Google Cloud Project ID as environment variable
export GOOGLE_CLOUD_PROJECT=$(gcloud config get-value project)

# Clone the github project
# git clone <link>

# Create IAM for jenkins account and add permissions to the GCP
gcloud iam service-accounts create jenkins-sa --display-name "jenkins-sa"

gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
    --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
    --role "roles/viewer"

gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
    --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
    --role "roles/source.reader"

gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
    --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
    --role "roles/storage.admin"

gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
    --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
    --role "roles/storage.objectAdmin"

gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
    --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
    --role "roles/cloudbuild.builds.editor"

gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
    --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
    --role "roles/container.developer"

# Create JSON key
gcloud iam service-accounts keys create ~/jenkins-sa-key.json --iam-account "jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com"


###############################
##### Kubernetes Engine #######
###############################

# Create Kubernetes cluster for jenkins
gcloud container clusters create jenkins-cd \
  --num-nodes 2 \
  --machine-type n1-standard-2 \
  --cluster-version 1.15 \
  --service-account "jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com"

# Get the credentials
gcloud container clusters get-credentials jenkins-cd

# To see the kubernetes pods
kubectl get pods

# Add ourself in the cluster RBAC
kubectl create clusterrolebinding cluster-admin-binding --clusterrole=cluster-admin --user=$(gcloud config get-value account)


###############################
######## Install Helm #########
###############################

# Download Helm, unzip and copy the helm into our working directory
wget https://get.helm.sh/helm-v3.2.1-linux-amd64.tar.gz
tar zxfv helm-v3.2.1-linux-amd64.tar.gz
cp linux-amd64/helm .

# Add stable chart
./helm repo add stable https://kubernetes-charts.storage.googleapis.com

# Verify Installation
./helm version


###################################
## Configure and Install Jenkins ##
###################################

# Install Jenkins based on jenkins/values.yaml
./helm install cd-jenkins -f jenkins/values.yaml stable/jenkins --version 1.7.3 --wait

# Verify installation and running
kubectl get pods

# Set the credentials to jenkins
kubectl create clusterrolebinding jenkins-deploy --clusterrole=cluster-admin --serviceaccount=default:cd-jenkins

# Set up port forwarding on Cloud Shell
export JENKINS_POD_NAME=$(kubectl get pods -l "app.kubernetes.io/component=jenkins-master" -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward $JENKINS_POD_NAME 8080:8080 >> /dev/null &

# To see if the Jenkins Service was created properly
kubectl get svc

###############################
###### Connect to Jenkins #####
###############################

# To get the password key
printf $(kubectl get secret cd-jenkins -o jsonpath="{.data.jenkins-admin-password}" | base64 --decode);echo

