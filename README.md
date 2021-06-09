# Coral-ID

**Coral-ID** is an Android application that can classify the taxonomy of corals through uploaded images or images taken. With Coral-ID, we wish to change from the traditional methods of coral identification to the modern method that is much more accurate, cheap, and fast. Therefore, it will help **to understand the ecology of corals and make better preservation plans**. This application also has an objective as a **citizen science project** for coral reefs. Since the coral can be easily identified using a trained model inside the app, anyone can partake in this citizen science project. We wish that through this project, the public will raise their awareness about crucial and threatened marine ecosystems like coral reefs.

# Android
## Screenshots
<span>
  <img src="https://user-images.githubusercontent.com/55786451/121338453-b915b280-c947-11eb-9e09-7edc9a1f8e9c.png" width="180px" height="320px" />
  <img src="https://user-images.githubusercontent.com/55786451/121339758-047c9080-c949-11eb-8758-859a86d86fe3.png" width="180px" height="320px" />
  <img src="https://user-images.githubusercontent.com/55786451/121339762-06465400-c949-11eb-9810-7301c57e406f.png" width="180px" height="320px" />
  <img src="https://user-images.githubusercontent.com/55786451/121339857-1bbb7e00-c949-11eb-8045-a247dfdad73f.png" width="180px" height="320px" />
</span>
<br>
<br>
<span>
  <img src="https://user-images.githubusercontent.com/55786451/121339864-1d854180-c949-11eb-9f60-ee0d4454e46f.png" width="180px" height="320px" />
  <img src="https://user-images.githubusercontent.com/55786451/121339880-2413b900-c949-11eb-9f93-f3245b6452da.png" width="180px" height="320px" />
  <img src="https://user-images.githubusercontent.com/55786451/121339906-2bd35d80-c949-11eb-99df-a3e7733ba19f.png" width="180px" height="320px" />
  <img src="https://user-images.githubusercontent.com/55786451/121339911-2d9d2100-c949-11eb-8d5c-6678024ea0e9.png" width="180px" height="320px" />
</span>

## Features
* **Coral Book :**  show coral catalogue in details
* **Coral Detection:** use machine learning technology for identify and clasify coral species

## Links
* **APK File :** https://drive.google.com/drive/folders/1dbKQViNKph0JLqZ7YBE5QVwT1LwuTt9D?usp=sharing
* **Mockup Design File :** https://www.figma.com/file/hGxOUGbyAReN3L7U0Hn8oH/Coral-ID?node-id=67%3A1257 

## Architecture Diagram
This application strictly follows the below architecture:
<br>
<br>
<img src="https://user-images.githubusercontent.com/55786451/121332658-448c4500-c942-11eb-854b-64cecf22fb39.jpeg" width="400px" height="400px" />

## Tech-Stack
* **Android Jetpack :** https://developer.android.com/jetpack
* **Glide :** https://github.com/bumptech/glide
* **RoundedImageView:** https://github.com/vinc3m1/RoundedImageView
* **Retrofit2 :** https://square.github.io/retrofit/
* **Lottie :** https://github.com/airbnb/lottie-android
* **OkHttp :** https://square.github.io/okhttp/
* **Dagger2 :** https://github.com/google/dagger
* **CameraX :** https://github.com/android/camera-samples/tree/master
* **Zelory Compressor :** https://github.com/zetbaitsu/Compressor
* **Material Design Component :** https://material.io/

<br>

# Project Cloud using GCP (Google Cloud Platform)
## Description
In this project, I will used Docker Container and building an Django REST API for connecting Machine Learning model. To deploy, we only to run the setup script and deploy the kubernetes engine and jenkins. 

## References

- https://www.youtube.com/watch?v=Y_rh-VeC_j4 (dokcerizing Django API)
- [https://chriskyfung.github.io/blog/qwiklabs/deploy-to-kubernetes-in-google-cloud-challenge-lab](https://chriskyfung.github.io/blog/qwiklabs/deploy-to-kubernetes-in-google-cloud-challenge-lab)
- [https://chriskyfung.github.io/blog/qwiklabs/Implement-DevOps-in-Google-Cloud-Challenge-Lab](https://chriskyfung.github.io/blog/qwiklabs/Implement-DevOps-in-Google-Cloud-Challenge-Lab)
- [https://medium.com/avmconsulting-blog/kubernetes-ci-cd-using-jenkins-on-google-cloud-5b10da6147a6](https://medium.com/avmconsulting-blog/kubernetes-ci-cd-using-jenkins-on-google-cloud-5b10da6147a6)
- [https://github.com/GoogleCloudPlatform/continuous-deployment-on-kubernetes](https://github.com/GoogleCloudPlatform/continuous-deployment-on-kubernetes)
- [https://cloud.google.com/kubernetes-engine/docs/tutorials/hello-app](https://cloud.google.com/kubernetes-engine/docs/tutorials/hello-app)
- [https://cloud.google.com/architecture/continuous-delivery-jenkins-kubernetes-engine](https://cloud.google.com/architecture/continuous-delivery-jenkins-kubernetes-engine)
<br>

# Machine Learning
## Data Specification
The dataset is from [StructureRSMAS](https://sci2s.ugr.es/CNN-coral-image-classification) that contains 409 images separated by class and has 14 of classes. But since the application is about coral identification, our team focuses on 7 classes (ACER, APAL, CNAT, MALC, MCAV, MMEA, and SSID). For our baseline and transfer learning model, we generate more images using data augmentation (from 256 images to 1898 images).
<br>
<br>
![WhatsApp Image 2021-06-09 at 21 30 16 (1)](https://user-images.githubusercontent.com/68630584/121375679-1c650c00-c96b-11eb-875c-60f2db6aa72c.jpeg)

<br>

We also make bounding boxes for the YOLOv4 training model with makesense.ai, in which you can find out more [here](https://makesense.ai).
<br>
<br>
![WhatsApp Image 2021-06-09 at 21 30 16](https://user-images.githubusercontent.com/68630584/121375655-15d69480-c96b-11eb-8635-f9ad6418a500.jpeg)

## The Approach
In machine learning, we try three different scenarios for training models: baseline from CNN Coursera, transfer learning DenseNet121, and object detection using YOLOv4.
<br>
<br>
![flowchart](https://user-images.githubusercontent.com/68630584/121382435-cd21da00-c970-11eb-8fea-1fd4e7670a22.png)

And here’s the result of each model:
Algoritmn |Precision | Recall | F-1 Score
------------ |------------ | ------------- | -------------
Baseline CNN Coursera | 0.10 | 0.11 | 0.10
Transfer Learning DenseNet-121 | 0.15 | 0.17 | 0.15
Object Detection YOLOv4 | **0.81** | **0.81** | **0.81** 

The precision, recall, and F-1 score on the baseline model is really low and the transfer learning only improves them a little bit. Then with YOLOv4, we achieved 81% on precision, recall, and F-1 score and we think this is adequate for our model to run smoothly. Other reasons for using the YOLOv4 model: being able to **detect multiple classes in various conditions fast and accurately**.

## References
* [Bochkovskiy et al., 2020](https://arxiv.org/abs/2004.10934)
* [Gómez-Ríos et al., 2019](http://dx.doi.org/10.1016/j.knosys.2019.104891)
* [Huang et al., 2017](https://arxiv.org/abs/1608.06993)
* [The AI Guy - YOLO4 Cloud Tutorial](https://github.com/theAIGuysCode/YOLOv4-Cloud-Tutorial)

# People Behind Coral-ID
## Android
* Eldis Simone - eldisstti@gmail.com 
* Mohammad Arafat Maku - arafat1419@gmail.com 
## Cloud Computing
* Agustinus - agustinus.koo@protonmail.com
* Stephen Nicholas - stephennicholas_23719@protonmail.com 
## Machine Learning
* Dia Marganita - diamarganita@outlook.com
* Shintia Puspita Dewi - shintiapuspitad24@gmail.com 
