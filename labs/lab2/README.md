# CMPE 172 - Lab #2 Notes

## Spring MVC Project - Serving Web Content with Spring MVC

**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.11
   - Group: com.example
   - Artifact: spring-mvc
   - Name: spring-mvc
   - Package Name: come.example.spring-mvc
   - Packaging: Jar
   - Dependencies:
      - Spring Web
      - Thymeleaf
      - Spring Boot Devtools
      - Testcontainers
      
   Download the spring-mvc zip folder, extract, and place into the lab2 folder. Download *Dockerfile* from the [given lab files](https://github.com/paulnguyen/cmpe172/tree/main/labs) under cmpe172/labs/lab1/docker, and put this into the spring-mvc folder.
   
2. Open SpringMvcApplication in spring-mvc\src\main\java\com\example\springmvc in Visual Studio Code. Edit the code so that it looks like this:
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
   
3. Open the terminal in Visual Studio Code. On the top right of the terminal window, click on the drop-down menu next to the + sign. Click *Git Bash*. On the right side of the terminal window, you can click on the trash icon on the same line as *powershell* to kill it. Now, you should just have *bash* running. Run the following lines to build the Docker image, called *spring-mvc* in the current directory:
   ```
   docker build -t spring-mvc .
   ```
   Run the following line to check if the image has been built:
   ```
   docker images
   ```
   Run the following line to run the Docker container, which we will call *spring-mvc*, on port 80 from the image you just built:
   ```
   docker run --name spring-mvc -td -p 80:8080 spring-mvc
   ```
   Run the following line to check if the container is running:
   ```
   docker ps
   ```

4. Open Docker Desktop. The *spring-mvc* container should already be running. Click on *spring-mvc*>*Open in Browser*. Your machine's default browser should open and have the application running on localhost.

5. For future reference, upload the docker image to your Docker Hub account. On the same terminal, run the following lines to login into your Docker Hub, then upload your built *spring-mvc* Docker image (make sure that *$(account)* is changed to your own username on Docker Hub:
   ```
   docker login
   docker build -t $(account)/spring-mvc:latest -t $(account)/spring-mvc:latest .
   docker push $(account)/spring-mvc:latest 
   ```
   On your browser, log in to your Docker Hub account [here](https://hub.docker.com/), and your *spring-mvc* Docker image should appear on your dashboard. Delete the *spring-mvc* container on Docker Desktop, and exit Docker Desktop. Ensure that it is not running in the background by ending the program in Task Manager.

6. Log into your [Google Cloud Console](https://cloud.google.com/gcp?utm_source=google&utm_medium=cpc&utm_campaign=na-US-test-all-en-dr-bkws-all-all-trial-e-dr-1009892&utm_content=text-ad-none-any-DEV_c-CRE_491349594127-ADGP_Desk%20%7C%20BKWS%20-%20EXA%20%7C%20Txt%20~%20Storage%20~%20Cloud%20Storage_Cloud-KWID_43700060017921803-kwd-6458750523&utm_term=KW_google%20cloud-ST_google%20cloud&gclid=CjwKCAjw-ZCKBhBkEiwAM4qfF6KsfYUTnzI8bfp8t8HuyR_7zjpOCiG7A-xfOuO0ObfQFU5_pFVerxoCGBwQAvD_BwE&gclsrc=aw.ds). In the left navigation bar, go to *Kubernetes Engine>Clusters>Create>Configure GKE Standard*. Rename the cluster to *spring-mvc*, then on the left navigation bar go to *default-pool>Nodes* and change *Machine type* to *e2-small*. Finally, click *Create* at the bottom of the page.

7. After a few minutes, the cluster will be created. Download the *pod.yaml* and *service.yaml* files from the [given lab files](https://github.com/paulnguyen/cmpe172/tree/main/labs) under cmpe172/labs/lab1/docker. Change the code for *pod.yaml* so that it looks like this:
   ```
   apiVersion: v1
   kind: Pod
   metadata:
     name: spring-mvc
     namespace: default
     labels:
       name: spring-mvc
   spec:
     containers:
       - image: shayannag/spring-mvc:latest
         name: spring-mvc
         ports:
           - containerPort: 8080
             name: http
             protocol: TCP
   ```

   Change the code for *service.yaml* so that it looks like this:
   ```
   apiVersion: v1
   kind: Service
   metadata:
     name: spring-mvc
     namespace: default
   spec:
     # comment or delete the following line if you want to use a LoadBalancer
     type: LoadBalancer
     # if your cluster supports it, uncomment the following to automatically create
     # an external load-balanced IP for the frontend service.
     # type: LoadBalancer
     ports:
       - port: 80
         targetPort: 8080
     selector:
       name: spring-mvc
     ```
   Go back to your Google Cloud, and click *Connect>Run in cloud shell* to run the terminal. Click on the three dots at the top of the terminal window, and upload *pod.yaml* and *service.yaml*. 

8. Run the following lines in the terminal to remove any previous versions of *pod.yaml* and *service.yaml* before running the most recent version that we editted:
   ```
   rm pod.yaml service.yaml
   kubectl apply -f pod.yaml
   kubectl create -f service.yaml
   ```
   Click on *Services & Ingress>spring-mvc* and click on the IP address next to *External endpoints*. A new tab will open with a Whitelabel Error Page. Insert */hello?name=cmpe172* after the IP address in the address bar and refresh the page. The *Hello cmpe172!* should appear.
   
   Screenshots of the Spring MVC Application using Google Cloud are shown below:
   ![spring mvc 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/images/spring%20mvc%201.png)
   ![spring mvc 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/images/spring%20mvc%202.png)

9. End the cluster by going to *Clusters*, clicking on the checkbox next to your cluster, and selecting *Delete* at the top of the page. The deletion process will take a few minutes.

## Spring Test Project

**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.11
   - Group: com.example
   - Artifact: spring-test
   - Name: spring-test
   - Package Name: come.example.spring-test
   - Packaging: Jar
   - Dependencies:
      - Spring Web
      - Thymeleaf
      - Spring Boot Devtools
      - Testcontainers

   Download the spring-mvc zip folder, extract, and place into the lab2 folder. Test that it works by opening a terminal of your choice (I used the terminal in Visual Studio Code) and run the following lines:
   ```
   gradle build
   gradle bootRun
   ```
   *"Hello, World"* should appear on your browser when you go to *localhost:8080*.
   
2. Open Visual Studio Code, and add 3 new java files called *HomeController.java*, *GreetingController.java*, and *GreetingService.java* under *spring-test/src/main/__java__/com/example/springtest*. Copy and paste the code from [*HomeController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-test/src/main/java/com/example/springtest/HomeController.java), [*GreetingController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-test/src/main/java/com/example/springtest/GreetingController.java), and [*GreetingService.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-test/src/main/java/com/example/springtest/GreetingService.java) from this repository, and save. 

3. Similarly, you are going to do the same thing for [*HTTPRequest.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-test/src/test/java/com/example/springtest/HTTPRequestTest.java), [*SmokeTest.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-test/src/test/java/com/example/springtest/SmokeTest.java), [*SpringTestApplication.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-test/src/test/java/com/example/springtest/SpringTestApplicationTests.java), [*WebLayerTest.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-test/src/test/java/com/example/springtest/WebLayerTest.java), and [*WebMockTest.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-test/src/test/java/com/example/springtest/WebMockTest.java), except you will put these java files under *spring-test/src/__test__/java/com/example/springtest*. 
 
4. In the same or a new terminal in Visual Studio Code, run the following lines to run *spring-test*:
   ```
   gradle clean
   gradle build
   gradle bootRun
   ```
   Now, open your *spring-test* folder in the file explorer, and navigate to *spring-test/build/reports/tests/test*. Click on *index.html*, and your default browser should automatically open. On the webpagem click on the *Classes* button, and a full test report should appear. 
   
   A screenshot of the Spring Test Application is shown below:
   ![spring test](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/images/spring%20test.png)
   
## Spring Lombok

**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.11
   - Group: com.example
   - Artifact: spring-lombok
   - Name: spring-lombok
   - Package Name: come.example.spring-lombok
   - Packaging: Jar
   - Dependencies:
      - Spring Web
      - Validation
      - Lombok

   Download the spring-lombok zip folder, extract, and place into the lab2 folder. 
   
2. Open the *spring-lombok* folder in Visual Studio Code. Add [*ConstructorUserDemo.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-lombok/src/main/java/com/example/springlombok/ConstructorUserDemo.java), [*DataUserDemo.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-lombok/src/main/java/com/example/springlombok/DataUserDemo.java), [*FieldLevelGetterSetterDemo.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-lombok/src/main/java/com/example/springlombok/FieldLevelGetterSetterDemo.java), [*GetterSetterUserDemo.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-lombok/src/main/java/com/example/springlombok/GetterSetterUserDemo.java), [*NonNullUserDemo.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-lombok/src/main/java/com/example/springlombok/NonNullUserDemo.java), [*SpringLombokApplication.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-lombok/src/main/java/com/example/springlombok/SpringLombokApplication.java), [*ValAndVarUserDemo.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/spring-lombok/src/main/java/com/example/springlombok/ValAndVarUserDemo.java) under *spring-lombok/src/main/java/com/example/springlombok*. Save these files. 

3. Open a new terminal in VIsual Studio Code. Run the following lines:
   ```
   gradle clean
   gradle build
   gradle bootRun
   ```
   On your browser of choice (I used Google Chrome), go to *http://localhost:8080/hello?name=cmpe172*, and *Hello cmpe172!* should appear on the webpage. Go back to your terminal, and a test report should appear. 
   
   Screenshots of the Spring Lombok Application is shown below:
   ![spring lombok 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/images/spring%20lombok%201.png)
   ![spring lombok 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/images/spring%20lombok%202.png)
   ![spring lombok 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/images/spring%20lombok%203.png)
   ![spring lombok 4](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab2/images/spring%20lombok%204.png)
  

**QUESTION: How does the Output verify your understanding of the Feature being tested?**
1. ValAndVarUserDemo
   - valCheck() assigns a string, *Hello World*, to userName, prints its class and the same string but all lower case. 
   - varCheck() assigns *53.00* to a variable, *money*. The value of *money* is then changed to *80.00*. The class of *money* is printed, along with its new value of *80*.
   
2. FieldLevelGetterSetterDemo Bytecode Dump 
   - *@Getter* automatically creates a getter method, while *@Setter* automatically generates a setter method. In the print message, we can see that getter methods were made for *userName* and *userAge*. This is done at the field level *within* the method.

3. GetterSetterUserDemo Bytecode Dump 
   - Similar to *FieldLevelGetterSetterDemo*, *@Getter* and *@Setter* methods are automatically made for the variables: *userId*, *userName*, and *userAge*. This is done at the class level outside the class declaration. 

4. ConstructorUserDemo Bytecode Dump 
   - *@NoArgsConstructor* automatically creates a constructor that does not take any arguments, while *@AllArgsConstructor* automatically creates a constructor that does take in arguments. Here, the print message shows that 2 constructors were created, one of each type mentioned earlier.
   
5. DataUserDemo Bytecode Dump 
   - *@Data* combines the annotations: *@ToString*, *@EqualsAndHashCode*, *@Getter*, *@Setter*, and *@RequiredArgsConstructor*. *Getter* and *setter* methods are created for the variables: *userId*, *userName*, and *userAge*. *toString*, *equals*, and *hashCode* methods are also automatically made, according to the print message.

6. NonNullUserDemo 
   - Because *NonNullUserDemo.java* is not annotated with *@NonNull*, then the following error message is printed:
   ```
   java.lang.NullPointerException: userName is marked non-null but is null 
   ```
