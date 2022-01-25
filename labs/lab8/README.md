# CMPE 172 - Lab #8 Notes

## Install Kong on Local Docker
**Steps:**

1. Go to https://konghq.com/install, and select the *Docker* button in the *Containerized* section. Open *Visual Studio Code* and *Docker Desktop*. Once the Docker engine is running, run the commands for steps 1-5 from the *KongHQ* website.  If you find that the commands that have multiple lines return errors on your terminal, then copy and paste them into a separate text editor, remove the \ at the end of each line, and put everything on one line. In commands that include *{HOSTNAME}*, replace this term to *Localhost*. The commands may pull images from *DockerHub* if they are not yet pulled to your machine.

2. Check that the containers are properly running on Docker Desktop. Verify your Kong installation in step 6. If the terminal returns the correct output, delete the containers (we will be running our own version of this for *starbucks-api*).

## Run Starbucks API in Docker
**Steps:**
1. First, create a Docker image for *starbucks-api*. Copy the *starbucks-api* folder from lab 5 and paste this into your lab 8 folder. Then, open this folder in *Visual Studio Code*. Copy and paste the contents of [*Dockerfile*]() and [*docker.sh*]() into files of the same name under the *starbucks-api* folder. Make sure that the *version* in [*build.gradle*]() is set to *1.0*. Open a new terminal in *Visual Studio Code* and ensure that *Docker Desktop* is still running. Run the following command to create a the *spring-starbucks-api* Docker image. 
   ```
   docker build -t spring-starbucks-api .
   ```
   
2. Upload this image to your own DockerHub account by running the following commands (replace *$(account)* to your own DockerHub username):
   ```
   docker login
   docker build -t $(account)/spring-starbucks-api:latest -t $(account)/spring-starbucks-api:latest .
   docker push $(account)/spring-starbucks-api:latest 
   ```
   
3. Create a new file called [*kong.yaml*]() under the *starbucks-api* folder and copy and paste the contents from the respective link.   
   
4. Create the Kong Docker Network by running the following command:
   ```
   docker network create --driver bridge kong-network
   ```
   
   Then, check that it has been successfully created using the following command (*kong-network* should appear on the terminal):
   ```
   docker network ls
   ```
   
5. Now, you can run Kong Docker in DB-less mode by running the following command:
   ```
   docker run -d --name kong --network=kong-network -e "KONG_DATABASE=off" -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" -e "KONG_PROXY_ERROR_LOG=/dev/stderr" -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" -p 80:8000 -p 443:8443 -p 8001:8001 -p 8444:8444 kong:2.4.0
   ```
   It may pull another image for *kong 2.4.0* since the lab uses this version instead of the latest one from the Kong installation guide. 
   
6. Run the *starbucks-api* container using the *spring-starbucks-api* image by running the following command:
   ```
   docker run -d --name starbucks-api --network kong-network -td spring-starbucks-api
   ```
   
   A screenshot of the Starbucks API container on Docker is shown below:
   ![starbucks api cotnainer](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/starbucks%20api%20container.png)
      
7. Ensure that you have *httpie* installed. If not, install [Chocolatey](https://community.chocolatey.org/courses/installation/installing) using the command prompt as Administrator (if you are using Windows like me), then install [httpie](https://httpie.io/) in the same command prompt. Now, you can run the *starbucks-api* application by running the following command: 
   ```
   http :8001/config config=@kong.yaml
   ```

   Screenshots of Starbucks API running on DB-less mode is shown below:
   ![starbucks api httpie 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/starbucks%20api%20httpie%201.png)
   ![starbucks api httpie 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/starbucks%20api%20httpie%202.png)
   ![starbucks api httpie 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/starbucks%20api%20httpie%203.png)


## Deploy Kong on Google GKE
**Steps:**

1. Download the following *.yaml* files, and save them under the *starbucks-api* folder (there should be files in total): [*deployment.yaml*](), [*ingress.yaml*](), [*jumpbox.yaml*](), [*kong.yaml*](), [*kong-container.yaml*](), [*kong-credentials.yaml*](), [*kong-ingress-controller.yaml*](), [*kong-ingress-rule.yaml*](), [*kong-key-path.yaml*](), [*kong-strip-path.yaml*](), [*pod.yaml*](), and [*service.yaml*](). *Deployment.yaml* ill be slightly different for you as you should use your own *spring-starbucks-api* Docker image.

2. Log into your [Google Cloud Console](https://cloud.google.com/gcp?utm_source=google&utm_medium=cpc&utm_campaign=na-US-test-all-en-dr-bkws-all-all-trial-e-dr-1009892&utm_content=text-ad-none-any-DEV_c-CRE_491349594127-ADGP_Desk%20%7C%20BKWS%20-%20EXA%20%7C%20Txt%20~%20Storage%20~%20Cloud%20Storage_Cloud-KWID_43700060017921803-kwd-6458750523&utm_term=KW_google%20cloud-ST_google%20cloud&gclid=CjwKCAjw-ZCKBhBkEiwAM4qfF6KsfYUTnzI8bfp8t8HuyR_7zjpOCiG7A-xfOuO0ObfQFU5_pFVerxoCGBwQAvD_BwE&gclsrc=aw.ds). In the left navigation bar, go to *Kubernetes Engine>Clusters>Create>Configure GKE Standard*. Rename the cluster to *cmpe172*, then on the left navigation bar go to *default-pool>Nodes* and change *Machine type* to *e2-small*. Finally, click *Create* at the bottom of the page.

2. After a few minutes, the cluster will be created. Click *cmpe172>Connect>Run in cloud shell* to run the terminal. Click on the three dots at the top of the terminal window, and upload the 12 *.yaml* files. 

3. Run the following command to deploy the *Starbucks API* application:
   ```
   kubectl create -f deployment.yaml 
   ```
   
   ![starbucks deployment](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/starbucks%20deployment.png)
   
4. Run the following command to run the service for the *Starbucks API* application:
   ```
   kubectl create -f service.yaml
   ```
   
   ![starbucks service](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/starbucks%20service.png)
   
5. Now, create the *jumpbox* pod with the following command:
   ```
   kubectl create -f jumpbox.yaml
   ```
   
   ![jumpbox](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/jumpbox.png)
   
   To run jumpbox and test deployment, run the following lines:
   ```
   kubectl exec  -it jumpbox -- /bin/bash
	
   apt-get update
   apt-get install curl -y
   curl http://spring-starbucks-api-service:80/ping
   ```
   
   The following message should be returned:
   ```
   {"test": "Starbucks API version 2.0 alive!"}
   ```
   
6. Now, create the pod for the *ingress controller*:
   ```
   kubectl apply -f https://bit.ly/k4k8s
   ```
   
   On the left navigation bar, go to *Services & Ingress>kong-proxy* Copy the first IP Address after *External endpoints* (it should end with *80*), and paste this in place of the IP Address in the command below:
   ```
   export KONG=35.184.121.226
   echo $KONG
   curl -i $KONG
   ```
   
   The following message should be returned:
   ```
   {"message":"no Route matched with those values"}
   ```
   
   ![ingress controller](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/ingress%20controller.png)

7. Create the ingress rule to proxy the Starbucks service:
   ```
   kubectl apply -f kong-ingress-rule.yaml
   kubectl apply -f kong-strip-path.yaml
   kubectl patch ingress starbucks-api -p '{"metadata":{"annotations":{"konghq.com/override":"kong-strip-path"}}}'
   ```
   
   ![ingress rule](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/ingress%20rule.png)
   
8. Test the Kong API ping endpoint:
   ```
   curl $KONG/api/ping
   ```
   
   ![ping endpoint](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/ping%20endpoint.png)
   
9. Add the Kong key-auth plugin:
   ```
   kubectl apply -f kong-key-auth.yaml
   kubectl patch service spring-starbucks-api-service -p '{"metadata":{"annotations":{"konghq.com/plugins":"kong-key-auth"}}}'
   ```
   
   ![key-auth plugin](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/key-auth%20plugin.png)
   
10. Configure the API client key:
    ```
    kubectl apply -f kong-consumer.yaml
    ```
    
    ![client key](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/client%20key.png)
    
11. Create the Kubernetes secret key:
    ```
    kubectl create secret generic apikey --from-literal=kongCredType=key-auth   --from-literal=key=Zkfokey2311
    ```
    
12. Apply the API key credentials to the API client
    ```
    kubectl apply -f kong-credentials.yaml
    ```
    ![secret key](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab8/images/secret%20key.png)
    
## Discussion
**1. Any challenges you face while working on this lab (i.e. GKE deployment issues) and how you overcame / solved them.**
     My team members and I faced a few challenges when running Kong on Docker. We had to change some parts of the command lines and take .yaml files from previous classes and change them to fit this lab. For me personally, I had trouble printing the output for httpie because I did not know this whole time that I did not install it on my Visual Studio Code. I did not have many issues when running the lab on Google Cloud because I followed the demo from spring semester.

**2. A discussion of what changes would be needed in order to deploy your Starbucks API with MySQL / Cloud SQL.**
     In order to deploy the Starbucks API on Google Cloud, we would need to create a separate container for MySQL and connect this to our application also running on the cloud. 
