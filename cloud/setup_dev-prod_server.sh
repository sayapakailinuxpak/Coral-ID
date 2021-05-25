# 
git clone https://github.com/GoogleCloudPlatform/continuous-deployment-on-kubernetes
cd continuous-deployment-on-kubernetes/sample-app

#
kubectl create clusterrolebinding jenkins-deploy \
    --clusterrole=cluster-admin --serviceaccount=default:cd-jenkins

# create Kubernetes development and production
kubectl create ns development
kubectl create ns production

# create the canary and development deployments and services
kubectl --namespace=development apply -f k8s/development
kubectl --namespace=development apply -f k8s/canary
kubectl --namespace=development apply -f k8s/services

# create the canary and production deployments and services
kubectl --namespace=production apply -f k8s/production
kubectl --namespace=production apply -f k8s/canary
kubectl --namespace=production apply -f k8s/services

# Scale up the production environment frontends
kubectl --namespace=development scale deployment coralid-frontend-development --replicas=1
kubectl --namespace=production scale deployment coralid-frontend-production --replicas=2

# Retrieve the external IP for the production services
kubectl --namespace=production get service gceme-frontend

# Store IP service load balancer
export FRONTEND_SERVICE_IP-DEV=$(kubectl get -o jsonpath="{.status.loadBalancer.ingress[0].ip}"  --namespace=development services coralid-frontend)
export FRONTEND_SERVICE_IP-PRODUCTION=$(kubectl get -o jsonpath="{.status.loadBalancer.ingress[0].ip}"  --namespace=production services coralid-frontend)

# Creating a repository to host the sample app source code
# gcloud source repos create gceme
# git init
# git config credential.helper gcloud.sh
# export PROJECT_ID=$(gcloud config get-value project)
# git remote add origin https://source.developers.google.com/p/$PROJECT_ID/r/gceme
# git config --global user.email "[EMAIL_ADDRESS]"
# git config --global user.name "[USERNAME]"
# git add .
# git commit -m "Initial commit"
# git push origin master


#Deploying a development branch
# 1 Create another branch and push it to the Git server
git checkout -b dev-branch
git push origin dev-branch

# Start the proxy in the background:
kubectl proxy &

# Verify that your application is accessible by using localhost:
curl http://localhost:8001/api/v1/namespaces/new-feature/services/coralid-frontend:80/proxy/

git checkout canary
git merge new-feature
git push origin canary

git checkout master
git merge canary
git push origin master

git push origin :new-feature
kubectl delete ns new-feature
