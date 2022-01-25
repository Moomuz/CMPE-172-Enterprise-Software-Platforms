# CMPE 172 - Lab #10 Notes

Link to public repository: https://github.com/Moomuz/spring-gumball

## CI Workflow Part 1
**Steps:**

1. Download the starter files for lab 9 from https://github.com/paulnguyen/cmpe172/tree/main/labs/lab10, and put the *spring-gumball* folder in your *lab10* folder. 

2. On your own GitHub account, create your own public repository called *spring-gumball*. Take all of the files from the *spring-gumball* folder you just downloaded, and push them into the new repository you just made. All of the individual files should be at root level. 

   ![spring-gumball public repo](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/spring-gumball%20public%20repo.png)

3. Click on the *Actions* tab, and search for a box titled *Java with Gradle*. Click on *Set up this workflow*. If you cannot find it right away, click on *More continuous integration wokflows...* towards the bottom of the page, and the box should show up. 

   ![java with gradle](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/java%20with%20gradle.png)

4. In the editor, replace the code with the following below:
   ```
   name: Java CI with Gradle

   on:
     push:
       branches: [ main ]
     pull_request:
       branches: [ main ]

   jobs:
     build:

       runs-on: ubuntu-latest

       steps:
       - uses: actions/checkout@v2
       - name: Set up JDK 11
         uses: actions/setup-java@v2
         with:
           java-version: '11'
           distribution: 'adopt'
       - name: Grant execute permission for gradlew
         run: chmod +x gradlew
       - name: Build with Gradle
         run: ./gradlew build
       - name: Build Result
         run: ls build/libs
       - name: Upload a Build Artifact
         uses: actions/upload-artifact@v2.2.3
         with:
           name: spring-gumball
           path: build/libs/spring-gumball-2.0.jar
    ```
    Then, click on *Start commit*. Ensure that *commit directly to the main branch* is checked, and click *Commit new file*. A new hidden folder called *./github/workflows* should be created.
    
    ![gradle.yml](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/gradle.yml.png)
    ![workflows folder](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/workflows%20folder.png)
    
5. Similar to before, create another workflow with *Build and Deploy to GKE*. This time in the editor, change *cluster-1* to *cmpe172*, *gke-test* to *spring-gumball*, and *static-site* to *spring-gumball*. Commit these new changes.
   ```
   name: Build and Deploy to GKE

   on:
     release:
       types: [created]

   env:
     PROJECT_ID: ${{ secrets.GKE_PROJECT }}
     GKE_CLUSTER: cmpe172    # TODO: update to cluster name
     GKE_ZONE: us-central1-c   # TODO: update to cluster zone
     DEPLOYMENT_NAME: spring-gumball # TODO: update to deployment name
     IMAGE: spring-gumball

   jobs:
     setup-build-publish-deploy:
       name: Setup, Build, Publish, and Deploy
       runs-on: ubuntu-latest
       environment: production

       steps:
       - name: Checkout
         uses: actions/checkout@v2

       # Build JAR File
       - name: Set up JDK 11
         uses: actions/setup-java@v2
         with:
           java-version: '11'
           distribution: 'adopt'
       - name: Grant execute permission for gradlew
         run: chmod +x gradlew
       - name: Build with Gradle
         run: ./gradlew build
       - name: Build Result
         run: ls build/libs

       # Setup gcloud CLI
       - uses: google-github-actions/setup-gcloud@v0.2.0
         with:
           service_account_key: ${{ secrets.GKE_SA_KEY }}
           project_id: ${{ secrets.GKE_PROJECT }}

       # Configure Docker to use the gcloud command-line tool as a credential
       # helper for authentication
       - run: |-
           gcloud --quiet auth configure-docker

       # Get the GKE credentials so we can deploy to the cluster
       - uses: google-github-actions/get-gke-credentials@v0.2.1
         with:
           cluster_name: ${{ env.GKE_CLUSTER }}
           location: ${{ env.GKE_ZONE }}
           credentials: ${{ secrets.GKE_SA_KEY }}

       # Build the Docker image
       - name: Build
         run: |-
           docker build \
             --tag "gcr.io/$PROJECT_ID/$IMAGE:$GITHUB_SHA" \
             --build-arg GITHUB_SHA="$GITHUB_SHA" \
             --build-arg GITHUB_REF="$GITHUB_REF" \
             .

       # Push the Docker image to Google Container Registry
       - name: Publish
         run: |-
           docker push "gcr.io/$PROJECT_ID/$IMAGE:$GITHUB_SHA"

       # Set up kustomize
       - name: Set up Kustomize
         run: |-
           curl -sfLo kustomize https://github.com/kubernetes-sigs/kustomize/releases/download/v3.1.0/kustomize_3.1.0_linux_amd64
           chmod u+x ./kustomize

       # Deploy the Docker image to the GKE cluster
       - name: Deploy
         run: |-
           ./kustomize edit set image gcr.io/PROJECT_ID/IMAGE:TAG=gcr.io/$PROJECT_ID/$IMAGE:$GITHUB_SHA
           ./kustomize build . | kubectl apply -f -
           #kubectl rollout status deployment/$DEPLOYMENT_NAME
           #kubectl get services -o wide
   ```

   ![google.yml](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/google.yml.png)

6. Navigate back to the *Code* tab, and open *deployment.yaml* in the GitHub editor. Quickly change change the number of replicas from 4 to 2. 

7. Go back to the *Actions* tab, and check that the change in *deployment.yaml* is logged in *Actions*. A green indicator means that the code has been properly built and tested using the workflows we just set up. A yellow indicator means the workflow is still in progress of building the code. A red indicator means there was an error in building the code. In this case, this happened because I open the file in the github editor and exited the page without saving changes. However, since I did not make any commits to the repository, there is no actual error in the code.

   ![deployment.yml 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/deployment.yml%201.png)
   ![deployment.yml 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/deployment.yml%202.png)

8. To further inspect the change to *deployment.yaml*, click on the first *deployment.yaml* then *build*. Open the dropdown arrow for *Post Set Up JDK 11* and *Post Run actions/checkout@v2*.

   A screenshot of the results are shown below: 
   ![gradle run](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/gradle%20run.png)
   
## CI Workflow Part 2
**Steps:**

1. Log into your [Google Cloud Console](https://cloud.google.com/gcp?utm_source=google&utm_medium=cpc&utm_campaign=na-US-test-all-en-dr-bkws-all-all-trial-e-dr-1009892&utm_content=text-ad-none-any-DEV_c-CRE_491349594127-ADGP_Desk%20%7C%20BKWS%20-%20EXA%20%7C%20Txt%20~%20Storage%20~%20Cloud%20Storage_Cloud-KWID_43700060017921803-kwd-6458750523&utm_term=KW_google%20cloud-ST_google%20cloud&gclid=CjwKCAjw-ZCKBhBkEiwAM4qfF6KsfYUTnzI8bfp8t8HuyR_7zjpOCiG7A-xfOuO0ObfQFU5_pFVerxoCGBwQAvD_BwE&gclsrc=aw.ds). In the left navigation bar, go to *Kubernetes Engine>Clusters>Create>Configure GKE Standard*. Rename the cluster to *cmpe172*, and create it.

2. Navigate to *IAM>Service Accounts>Create Service Account*. Input *spring-gumball* as *Service account name* and *Service account ID*. Then for roles, select *Owner, Kubernetes Engine Developer, Storage Admin, and Service Account Admin*. Click *Done*. Click on the service account you just created, and click on *Keys*.

   ![new service account](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/new%20service%20account.png)

3. Click on *Add Key*, then ensure that the key type is set to *JSON*, and click *Create*. A JSON file should be downloaded to your local machine. 

   ![secret key](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/secret%20key.png)

4. Go back to your public GitHub repository. Navigate to *Settings>Secrets*. Click on *New repository secret*, and input the name as *GKE_PROJECT*. Copy just the value of *project_id* from the JSON file you just downloaded, and paste it into the *Value* textbox on GitHub. Click on *Add secret* to create the new secret. Do the same thing a second time, except the name should be *GKE_SA_KEY*, and you would copy the whole content of the same JSON file. 

   ![repo secrets](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/repo%20secrets.png)

5. Go back to the *Code* tab, and click on *Create a new release* on the right side of the webpage. Click on *Choose a tag*. type *2.4*, and select *Create new tag: 2.4 on publish" below it. Set the *Release title* to *2.4* as well. Now, click on *Publish release*.

   ![new release 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/new%20release%201.png)
   ![new release 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/new%20release%202.png)

6. Go back to *Actions* and click on the previous *Build and Deploy to GKE* workflow, then click on *Re-run all jobs* to re-run the workflow. Once the job is done, you can check Google Cloud and ensure the *service* and *deployment* pods are running. 

   ![actions after release](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/actions%20after%20release.png)
   ![workflow after release 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/workflow%20after%20release%201.png)
   ![workflow after release 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/workflow%20after%20release%202.png)
   ![workflow after release 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/workflow%20after%20release%203.png)
   
   ![gke deployment](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/gke%20deployment.png)
   ![gke service](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/gke%20service.png)

7. Now, go to *Services & Ingress>select the checkbox next to spring-gumball-service>Create Ingress*. In the *name* field, enter *spring-gumball-lb*. Go to *Host & path rules*, and select *spring-gumball-service* as the *Backend*. Finally, click *Create* to create the ingress. Now, navigate to *Services & Ingress>Ingress*, and you should see that the pod for *spring-gumball-lb* has been created.

   ![create ingress](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/create%20ingress.png)
   ![spring-gumball-lb](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/spring-gumball-lb.png)
   
8. Now, you can see the deployed *spring-gumball* application by clicking on the IP Address under *Frontends*. A new tab should open with the spring-gumball application perfectly running.

   ![spring-gumball](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab10/images/spring-gumball.png)
