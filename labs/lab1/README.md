# CMPE 172 - Lab #1 Notes

Before starting, download and extract the [given lab files](https://github.com/paulnguyen/cmpe172/tree/main/labs) somewhere you can easily access on you local computer.

**As a Windows user, I configured Gradle/Maven on a third-party Windows Terminal called [ConEmu](https://conemu.github.io/).**

## Spring Demo App Using Spring Initializr
**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.2
   - Group: com.example
   - Artifact: demo-initializr
   - Name: demo-initializr
   - Package Name: come.example.demo-initializr
   - Packaging: Jar
   - Dependencies:
     - Spring Web
   
   Download the demo-initializr zip folder, extract, and place into the lab1 folder.


2. Open DemoInitializrApplication in demo-initializr\src\main\java\com\example\demoinitializr in any IDE (I used Visual Studio Code). Edit the code so that it looks like this:
   ```
   package com.example.demoinitializr;

   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RequestParam;
   import org.springframework.web.bind.annotation.RestController;

   @SpringBootApplication
   @RestController
   public class DemoInitializrApplication {

       public static void main(String[] args) {
           SpringApplication.run(DemoInitializrApplication.class, args);
       }

       @GetMapping("/hello")
       public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
           return String.format("Hello %s!", name);
       }
   }
   ```
   Save this file.

3. Open ConEmu. Cd into the demo-initalizr folder, then run the following commands:
   ```
   gradle clean
   gradle build
   gradle bootRun
   ```

4. Open your browser of choice (I used Google Chrome). Run localhost on your machine with the following link:
   ```
   http://localhost:8080/hello?name=cmpe172
   ```
   A screenshot of the Spring Demo Application is shown below:
   ![initializr screenshot](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab1/images/initializr%20screenshot.png)

## Spring Demo App Using Spring Tools in Visual Studio Code
**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.2
   - Group: com.example
   - Artifact: demo-vscode
   - Name: demo-vscode
   - Package Name: come.example.demo-vscode
   - Packaging: Jar
   - Dependencies:
      - Spring Web
      
   Download the demo-vscode zip folder, extract, and place into the lab1 folder.


2. Open DemoVscodeApplication in demo-vscode/src/main/java/com/example/demovscode in Visual Studio Code. Edit the code so that it looks like this:
   ```
   package com.example.demovscode;

   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RequestParam;
   import org.springframework.web.bind.annotation.RestController;

   @SpringBootApplication
   @RestController
   public class DemoVscodeApplication {

       public static void main(String[] args) {
           SpringApplication.run(DemoVscodeApplication.class, args);
       }

       @GetMapping("/hello")
       public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
           return String.format("Hello %s!", name);
       }
   }
   ```
   Save this file.
   
3. Add a break to line 19 (return String.format("Hello %s!", name);). Go to Run > Start Debugging. Open the terminal and you should see that Spring is running. Similar to before, open your browser of choice (I used Google Chrome), and run localhost on your machine with the following link:
   ```
   http://localhost:8080/hello?name=cmpe172
   ```
   A screenshot of the Spring Demo Application using Visual Studio Code is shown below:
![vscode screenshot](https://github.com/Moomuz/CMPE-172-Enterprise-Software-Platforms/blob/main/labs/lab1/images/vscode%20screenshot.png)

## Spring Demo App Configured for Docker
**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.2
   - Group: com.example
   - Artifact: demo-docker
   - Name: demo-docker
   - Package Name: come.example.demo-docker
   - Packaging: Jar
   - Dependencies:
      - Spring Web
      
   Download the demo-docker zip folder, extract, and place into the lab1 folder. Download *Dockerfile* from the [given lab files](https://github.com/paulnguyen/cmpe172/tree/main/labs) under cmpe172/labs/lab1/docker, and put this into the demo-docker folder.
   
2. Open DemoVscodeApplication in demo-vscode\src\main\java\com\example\demovscode in Visual Studio Code. Edit the code so that it looks like this:
   ```
   package com.example.demodocker;

   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RequestParam;
   import org.springframework.web.bind.annotation.RestController;

   @SpringBootApplication
   @RestController
   public class DemoDockerApplication {

       public static void main(String[] args) {
           SpringApplication.run(DemoDockerApplication.class, args);
       }

       @GetMapping("/hello")
       public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
           return String.format("Hello %s!", name);
       }
   }
   ```
   Save this file.
   
3. Open ConEmu. Cd into the demo-docker folder. Run the following line to build the Docker image, called *spring-hello* in the current directory:
   ```
   docker build -t spring.hello .
   ```
   Run the following line to check if the image has been built:
   ```
   docker images
   ```
   Run the following line to run the Docker container, which we will call *spring-hello*, on port 80 from the image you just built:
   ```
   docker run --name spring-hello -td -p 80:8080 spring-hello
   ```
   Run the following line to check if the container is running:
   ```
   docker ps
   ```

4. Open Docker Desktop. The *spring-hello* container should already be running. Click on *spring-hello*>*Open in Browser*. Your machine's default browser should open and have the application running on localhost.

A screenshot of the Spring Demo Application using Visual Studio Code is shown below:
![docker screenshot](https://github.com/Moomuz/CMPE-172-Enterprise-Software-Platforms/blob/main/labs/lab1/images/docker%20screenshot.png)

5. For future reference, upload the docker image to your Docker Hub account. On the same ConEmu terminal, run the following lines to login into your Docker Hub, then upload your built *spring-hello* Docker image:
   ```
   docker login
   docker build -t $(account)/spring-hello:latest -t $(account)/spring-hello:latest .
   docker push $(account)/spring-hello:latest 
   ```
   On your browser, log in to your Docker Hub account [here](https://hub.docker.com/), and your *spring-hello* Docker image should appear on your dashboard. Delete the *spring-hello* container on Docker Desktop, and exit Docker Desktop. Ensure that it is not running in the background by ending the program in Task Manager.

## Spring Demo App Configured for Kubernetes
**Steps:**
1. Log into your [Google Cloud Console](https://cloud.google.com/gcp?utm_source=google&utm_medium=cpc&utm_campaign=na-US-test-all-en-dr-bkws-all-all-trial-e-dr-1009892&utm_content=text-ad-none-any-DEV_c-CRE_491349594127-ADGP_Desk%20%7C%20BKWS%20-%20EXA%20%7C%20Txt%20~%20Storage%20~%20Cloud%20Storage_Cloud-KWID_43700060017921803-kwd-6458750523&utm_term=KW_google%20cloud-ST_google%20cloud&gclid=CjwKCAjw-ZCKBhBkEiwAM4qfF6KsfYUTnzI8bfp8t8HuyR_7zjpOCiG7A-xfOuO0ObfQFU5_pFVerxoCGBwQAvD_BwE&gclsrc=aw.ds). In the left navigation bar, go to *Kubernetes Engine>Clusters>Create>Configure GKE Standard*. Rename the cluster to *spring-hello*, then on the left navigation bar go to *default-pool>Nodes* and change *Machine type* to *e2-small*. Finally, click *Create* at the bottom of the page.

2. After a few minutes, the cluster will be created. Download the *pod.yaml* and *service.yaml* files from the [given lab files](https://github.com/paulnguyen/cmpe172/tree/main/labs) under cmpe172/labs/lab1/docker. Click *Connect>Run in cloud shell* to run the terminal. Click on the three dots at the top of the terminal window, and upload *pod.yaml* and *service.yaml*. 

3. Run the following lines in the terminal:
   ```
   kubectl apply -f pod.yaml
   kubectl create -f service.yaml
   ```
   Click on *Services & Ingress>spring-hello* and click on the IP address next to *External endpoints*. A new tab will open with a Whitelabel Error Page. Insert */hello?name=cmpe172* after the IP address in the address bar and refresh the page. The *Hello cmpe172!* should appear.
   
A screenshot of the Spring Demo Application using Visual Studio Code is shown below:
![kubernetes screenshot](https://github.com/Moomuz/CMPE-172-Enterprise-Software-Platforms/blob/main/labs/lab1/images/kubernetes%20screenshot.pngg)

4. End the cluster by going to *Clusters*, clicking on the checkbox next to your cluster, and selecting *Delete* at the top of the page. The deletion process will take a few minutes.

## Spring Demo App Using JetBrains Intellij IDEA
**Steps:**
1. Open JetBrains Intellij IDEA. Start a new project with the following parameters:
   - Name: demo-intellij 
   - Group: com.example
   - Location: /labs/lab1/demo-intellij
   - Artifact: demo
   - Type: Maven
   - Language: Java
   - Packaging: Jar
   - Java Version: 11
   - Version: 1.0
   - Description: Demo project for Spring Boot
   - Package: com.example.demo
   
   Click *Next*. Make sure the *Spring Boot* version is set to 2.5.4. and checkmark the box next to *Web>Spring Web*. Click *Finish*.
   
2. Open *DemoIntellijApplication.java* under *src/main/java/com/example/demointellij*. Edit the code so that it looks like this:
   ```
   package com.example.demointellij;

   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RequestParam;
   import org.springframework.web.bind.annotation.RestController;

   @SpringBootApplication
   @RestController
   public class DemoIntellijApplication {

      public static void main(String[] args) {
         SpringApplication.run(DemoIntellijApplication.class, args);
      }

      @GetMapping("/hello")
      public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
         return String.format("Hello %s!", name);
      }
   }

   ```

3. Open *pom.xml*. Change the value between the version tag on line 13 to *1.0*. 

4. Open ConEmu. Cd into the demo-intellij folder, then run the following commands:
   ```
   mvn package
   java -jar target/demo-intellij-1.0.jar
   ```

5. Open your browser of choice (I used Google Chrome). Run localhost on your machine with the following link:
   ```
   http://localhost:8080/hello?name=cmpe172
   ```
   A screenshot of the Spring Demo Application is shown below:
   ![intellij screenshot](https://github.com/Moomuz/CMPE-172-Enterprise-Software-Platforms/blob/main/labs/lab1/images/intellij%20screenshot.png)
