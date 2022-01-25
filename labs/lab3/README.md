# CMPE 172 - Lab #3 Notes

## Spring Gumball on Docker
**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.11
   - Group: com.example
   - Artifact: spring-gumball
   - Name: spring-gumball
   - Package Name: come.example.spring-gumball
   - Packaging: Jar
   - Dependencies:
     - Spring Web
     - Thymeleaf
     - Testcontainers
     - Spring Boot Devtools
     - Lombok
     - Validation
   
   Download the spring-gumball zip folder, extract, and place into the lab3 folder. Then, download [*Dockerfile*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/Dockerfile) and [*docker-compose.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/docker-compose.yaml), and put these into the *spring-gumball* folder.
   
2. Now, open the *spring-gumball* folder in Visual Studio Code. We are going to add 3 java files into *spring-gumball/src/main/java/com/example/springgumball* called [*GumballCommand.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/java/com/example/springgumball/GumballCommand.java), [*GumballMachineController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/java/com/example/springgumball/GumballMachineController.java), and [*GumballModel.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/java/com/example/springgumball/GumballModel.java). Copy and paste the code for each respective file. 

3. Do the same thing for 6 more java files under *spring-gumball/src/main/java/com/example/gumballmachine*. These files are named [*GumballMachine.java*](), [*HasQuarterState.java*](https://github.com/paulnguyen/cmpe172/blob/main/starters/spring-gumball-v1/src/main/java/com/example/gumballmachine/HasQuarterState.java), [*NoQuarterState.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/java/com/example/gumballmachine/NoQuarterState.java), [*SoldOutState.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/java/com/example/gumballmachine/SoldOutState.java), [*SoldState.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/java/com/example/gumballmachine/SoldState.java), [*State.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/java/com/example/gumballmachine/State.java), and their code can be found in their respective links. 

4. For one last time, we are going to copy and paste the webpage assets for the frontend of the *Spring Gumball* application. The [*gumball.html*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/resources/templates/gumball.html) file will be put under *spring-gumball/src/main/resources/templates*, the [*style.css*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/resources/static/style.css) will be put under *spring-gumball/src/main/resources/static*, and the [*giant-gumball-machine.jpg*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/main/resources/static/images/giant-gumball-machine.jpg) will be put under *spring-gumball/src/main/resources/static/images*. 

5. Open the *build.gradle* file under *spring-gumball*. Change the version on line 8 to *1.0*. Now, test if the application works locally using the following commands in a new Visual Studio Code terminal:
   ```
   gradle clean
   gradle build
   gradle bootRun
   ```
   Open your browser of choice (I used Google Chrome), and go to *localhost:8080*. The application should appear on the page. When you click on the *Insert Quarter* or *Turn Crank* buttons, messages will appear in the terminal.

6. To run the application on Docker, open *Docker Desktop*, and build the docker image then run it using the following lines in the same terminal window:
   ```
   docker build -t spring-gumball .
   docker images
   docker-compose up --scale gumball=2 -d	
   docker ps
   ```
   Now, open Docker Desktop. The *spring-gumball* container should already be running. Click on *spring-gumball_gumball1*>*Open in Browser* and *spring-gumball_gumball1*>*Open in Browser*. Your machine's default browser should open and have the application running on localhost, and the IP Adress at the bottom of each webpage should be different.
   
   A screenshot of the Spring Gumball Application using Docker is shown below:
![spring gumball docker](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/spring%20gumball%20docker.png)

## Jumpbox Testing
**Steps:**
1. Open a new *Git Bash* terminal in Visual Studio Code. While Docker is running in the background of your computer, run the following line to inspect the network (this is where you can find the course of the IP Adress):
   ```
   docker network inspect spring-gumball_network
   ```
   Take note of the IP Address of either *spring-gumball_gumball_1* or *spring-gumball_gumball_2*, as these are the containers you will be testing with Jumpbox later. I tested *spring-gumball_gumball_1*, which has the IP Adress of *172.21.0.2*, but yours will most likely be different.
   
2. Then, run the following line to create a new Docker image for *Jumpbox* and also run a new container with that new image:
   ```
   docker run --network spring-gumball_network --name jumpbox -t -d ubuntu
   docker exec -it jumpbox bash
   ```
   Run *ls* to ensure you are in the right folder. 
 
3. If this is the first time you are running *Jumpbox*, you will need to install *httpie* and *ping* to run the needed *Jumpbox* tests. Run the following lines to do so:
   ```
   apt-get update
   apt-get install httpie
   apt-get install iputils-ping
   ```
   
4. To run *httpie*, run the following command with your unique IP Address:
   ```
   http 172.21.0.2
   ```
   The html code for *spring gumball application* will appear on the terminal.
   
   Similarly, to run *ping*, run the following command with your unique IP Address:
   ```
   ping 172.21.0.2
   ```
   The ping information for *spring gumball application* will appear on the terminal. The process will continue to run until you run *Ctrl + C*.
   
   A screenshot of the Jumpbox running *httpie* is shown below:
   ![jumpbox httpie](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/jumpbox%20httpie.png)
   
   A screenshot of the Jumpbox running *ping* is shown below:
   ![jumpbox ping](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/jumpbox%20ping.png)
   
5. For future reference, upload the docker image to your Docker Hub account. On the same Visual Studio Code terminal, run the following lines to login into your Docker Hub, then upload your built *spring-gumball* Docker image:
   ```
   docker login
   docker build -t $(account)/spring-gumball:latest -t $(account)/spring-gumball:latest .
   docker push $(account)/spring-gumball:latest 
   ```
   On your browser, log in to your Docker Hub account [here](https://hub.docker.com/), and your *spring-gumball* Docker image should appear on your dashboard. Delete the *spring-gumball* container on Docker Desktop, and exit Docker Desktop. Ensure that it is not running in the background by ending the program in Task Manager.
   
## Testing with Test Containers and Selenium
**Steps:**
1. In Visual Studio Code, add [*BrowserContainerTest.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/test/java/com/example/springgumball/BrowserContainerTest.java) and *SpringGumballApplication.java*(https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/test/java/com/example/springgumball/SpringGumballApplicationTests.java) under *spring-gumball/src/test/java/com/example/springgumball*. Similarly, do the same thing for [*GumballMachineTests.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/src/test/java/com/example/gumballmachine/GumballMachineTests.java) under *spring-gumball/src/test/java/com/example/gumballmachine*.

2. Open a new terminal in Visual Studio Code and run the following line to run the test files:
   ```
   gradle test
   ```
   Now, open your *spring-gumball* folder in the file explorer, and navigate to *spring-gumball/build/reports/tests/test*. Click on *index.html*, and your default browser should automatically open. On the webpage, click on the *Classes* button, and a full test report should appear. 
   
   On my Windows machine, Gradle failed to execute all of the tests, and only passed with a 60% grade (see screenshot below). This is due an incompability issue with line 39 in *BrowserTestContainer.java* (this.container.getWebDriver().get( "http://host.docker.internal/:" + port + "/"), as this command is meant to work on a Mac machine. 
   
   A screenshot of the Spring Gumball Tests is shown below:
   ![spring gumball tests](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/spring%20gumball%20tests.png)

### Questions:
**When you load the page multiple times what do you see on the Gumball Home Pages for Server Host/IP?**
Since I worked on this lab over a few days (each time running a new session), the IP Addresses that appear on the webpage are different each time. At the time I grabbed the screenshot of *spring gumball* running on Docker, the Server Host/IP changed between *5f154f3494df/172.17.0.2* and *5f154f3494df/172.17.0.3*.

**Can you explain where that IP value comes from?**
The IP value comes from the two containers, *spring-gumball_gumball_1* and *spring-gumball_gumball_2* running under another container, *spring-gumball*. These values can also be found when running the *Docker inspect* command earlier outlined in these lab notes; there we can see more information about the networks and subnetworks that *spring gumball* is running in.

**Now, try to add some quarters and purchase some gumballs. Do you see the inventory depleting?  Are there any errors? Review to code for your answer.  No need to make any code changes to fix any issue.**
When running only one instance of the application, the inventory depletes as it should when your insert a quarter and turn the crank; however, when you run the application in different containers, the application crashes and shows a Whitelabel Error page on the browser.

**Look at the Session State Feature of the HA Loadbalancer (using COOKIES_ENABLED). Is there a setting that can avoid the error?  Why does it work (or Why not).**
You can set the *COOKIES_ENABLED* variable to *true* in the *docker-compose.yaml* file in order to fix this error. This works because the browser would save a cookie that describes a session ID. This session ID is then passed between the two separately-running containers so that those containers will have access to the same data and states.

## Spring Gumball on Google Cloud
**Steps:**
1. Log into your [Google Cloud Console](https://cloud.google.com/gcp?utm_source=google&utm_medium=cpc&utm_campaign=na-US-test-all-en-dr-bkws-all-all-trial-e-dr-1009892&utm_content=text-ad-none-any-DEV_c-CRE_491349594127-ADGP_Desk%20%7C%20BKWS%20-%20EXA%20%7C%20Txt%20~%20Storage%20~%20Cloud%20Storage_Cloud-KWID_43700060017921803-kwd-6458750523&utm_term=KW_google%20cloud-ST_google%20cloud&gclid=CjwKCAjw-ZCKBhBkEiwAM4qfF6KsfYUTnzI8bfp8t8HuyR_7zjpOCiG7A-xfOuO0ObfQFU5_pFVerxoCGBwQAvD_BwE&gclsrc=aw.ds). In the left navigation bar, go to *Kubernetes Engine>Clusters>Create>Configure GKE Standard*. Rename the cluster to *spring-gumball*, then on the left navigation bar go to *default-pool>Nodes* and change *Machine type* to *e2-small*. Finally, click *Create* at the bottom of the page.

2. After a few minutes, the cluster will be created. Copy and paste the content in [*deployment.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/deployment.yaml), [*ingress.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/ingress.yaml), [*pod.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/pod.yaml), and [*service.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/spring-gumball/service.yaml), and save these files in you *spring-gumball* folder. Click *spring-gumball>Connect>Run in cloud shell* to run the terminal. Click on the three dots at the top of the terminal window, and upload *deployment.yaml*, *ingress.yaml*,*pod.yaml*, and *service.yaml*. 

3. Run the following lines in the terminal:
   ```
   kubectl apply -f pod.yaml
   kubectl create -f deployment.yaml
   kubectl create -f ingress.yaml
   kubectl create -f service.yaml
   ```
   To ensure that *spring-gumball* and *spring-gumball-deployment* are running, you can navigate to *Workloads* on the left navigation bar. Similarly, *spring-gumball-services* can be viewed when you click on *Services & Ingress* on the same left navigation bar. Lastly, to navigate to *spring-gumball-ingress*, go to *Services & Ingress>Ingress*.
   
Screenshots of the Spring Gumball Deployment is shown below:
![spring gumball deployment 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/spring%20gumball%20deployment%201.png)
![spring gumball deployment 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/spring%20gumball%20deployment%202.png)

A screenshot of the Spring Gumball Ingress is shown below:
![spring gumball ingress](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/spring%20gumball%20ingress.png)

Screenshots of the Spring Gumball Services is shown below:
![spring gumball services 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/spring%20gumball%20services%201.png)
![spring gumball services 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/spring%20gumball%20services%202.png)

A screenshot of the Spring Gumball Application running on Google Cloud is shown below:
![spring gumball application](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab3/images/spring%20gumball%20application.png)

4. End the cluster by going to *Clusters*, clicking on the checkbox next to your cluster, and selecting *Delete* at the top of the page. The deletion process will take a few minutes.

### Questions:
**When you load the page multiple times what do you see on the Gumball Home Pages for Server Host/IP?**
I loaded the page 3 times, and each time I got different descriptions for Server Host/IP. 
```
Server Host/IP:  spring-gumball-deployment-57cc69d497-nlflw/10.112.2.8
Server Host/IP:  spring-gumball/10.112.0.4
Server Host/IP:  spring-gumball-deployment-57cc69d497-pfzv7/10.112.0.5
```

**Can you explain where that IP value comes from?**
The IP value comes from the each of the running pods in Google Cloud, under *spring-gumball-deployment*.

**Now, try to add some quarters and purchase some gumballs. Do you see the inventory depleting?  Are there any errors?**
After clicking on *Insert Quarter*, I immediately get a Whitelabel Error Page.

**Is there a feature in GCP Load Balancer and/or GKE that can resovle the error? Explain, Yes or No and Why or Why not**
Although I was not able to test this myself, I believe that the Cloud CDN on Google Cloud could be the key to resolving the Whitelabel Error. The Cloud CDN would cache the cookies similarly used when running *spring gumball* on Docker and store the session ID there for the pods to share and make the application work.
