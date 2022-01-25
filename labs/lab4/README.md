# CMPE 172 - Lab #4 Notes

## Spring LDAP
**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.11
   - Group: com.example
   - Artifact: spring-ldap
   - Name: spring-ldap
   - Package Name: come.example.spring-ldap
   - Packaging: Jar
   - Dependencies:
     - Spring Web
     - Spring Security
     - Spring LDAP
     - Embedded LDAP Server
     
   Download the spring-ldap zip folder, extract, and place into the lab4 folder.
  
2. Open the folder in Visual Studio Code. Create 2 new java files called [*HomeController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-ldap/src/main/java/com/example/springldap/HomeController.java) and [*WebSecurityConfig.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-ldap/src/main/java/com/example/springldap/WebSecurityConfig.java) under *spring-ldap/src/main/java/com/example/springldap*, and copy and paste the code from their respective links.  

3. Open *application.properties* under *spring-ldap/src/main/resources*. Copy the lines below and paste them into the file.
   ```
   spring.ldap.embedded.ldif=classpath:test-server.ldif
   spring.ldap.embedded.base-dn=dc=springframework,dc=org
   spring.ldap.embedded.port=8389
   ```
   In the same folder, create a new file called *test-server.ldif*, and copy and paste the code from [*here*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-ldap/src/main/resources/test-server.ldif).
  
4. Open *SpringLdapApplicationTests.java* under *spring-ldap\src\test\java\com\example\springldap*. Replace the current code with the code from [*here*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-ldap/src/test/java/com/example/springldap/SpringLdapApplicationTests.java). 

5. Now, open *build.gradle* under *spring-ldap*. Replace the contents of this file with the contents from [*here*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-ldap/build.gradle).

6. Open a new terminal in Visual Studio Code. Run the application locally using the following lines:
   ```
   gradle build
   gradle bootrun
   ```
   Open your preferred browser (I used Google Chrome), and navigate to *localhost:8080* in the address bar; a login page should appear. In the *username* and *password* text fields, enter *ben* and *benspassword*, respectively. *Welcome to the homepage!" should appear on the webpage when you press the *Sign In* button. Similarly, if your inputs are incorrect, an error message saying *Bad credentials* should appear on the login page.
   
   Screenshots of Spring LDAP running locally are shown below:
   ![spring ldap 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20ldap%201.png)
   ![spring ldap 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20ldap%202.png)
   ![spring ldap 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20ldap%203.png)

## Securing a Web Application
**Steps:**
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.11
   - Group: com.example
   - Artifact: spring-security
   - Name: spring-security
   - Package Name: come.example.spring-security
   - Packaging: Jar
   - Dependencies:
     - Spring Web
     - Spring Security
     - Thymeleaf
   
   Download the spring-security zip folder, extract, and place into the lab4 folder.
   
2. Open the folder in Visual Studio Code. Create 2 new java files called [*MvcConfig.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-security/src/main/java/com/example/springsecurity/MvcConfig.java) and [*WebSecurityConfig.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-security/src/main/java/com/example/springsecurity/WebSecurityConfig.java) under *spring-ldap/src/main/java/com/example/springsecurity*, and copy and paste the code from their respective links. 

3. Now, create 3 html files called [*hello.html*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-security/src/main/resources/templates/hello.html), [*hello.html*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-security/src/main/resources/templates/home.html), and [*hello.html*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-security/src/main/resources/templates/login.html) under *spring-security/src/main/resources/templates*, and copy and paste the code from their respective links. 

6. Open a new terminal in Visual Studio Code. Run the application locally using the following lines:
   ```
   gradle build
   gradle bootrun
   ```
   Open your preferred browser (I used Google Chrome), and navigate to *localhost:8080* in the address bar; a login page should appear. In the *username* and *password* text fields, enter *user* and *password*, respectively. *Welcome to the homepage!" should appear on the webpage when you press the *Sign In* button. Similarly, if your inputs are incorrect, an error message saying *Bad credentials* should appear on the login page.
   
   Screenshots of Spring Security running locally are shown below:
   ![spring security 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20security%201.png)
   ![spring security 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20security%202.png)
   ![spring security 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20security%203.png)
   ![spring security 4](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20security%204.png)
   
## Spring Gumball Version 2

### Preparation
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
     
   Download the spring-gumball zip folder, extract, and place into the lab4 folder. 
   
2. Now, open the *spring-gumball-v2* folder in Visual Studio Code. We are going to add 3 java files into *spring-gumball/src/main/java/com/example/springgumballv2* called [*GumballCommand.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/springgumball/GumballCommand.java), [*GumballMachineController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/springgumball/GumballMachineController.java), and [*GumballModel.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/springgumball/GumballModel.java). Copy and paste the code for each respective file. The code in *GumballMachineController.java* has been editted to show the HMAC on the bottom-left corner of the webpage instead of the session ID. To create the HMAC, we first capture the timestamp at which the webpage was loaded. This timestamp and a given key (from Professor Nguyen's *HMACTesster.java* example) are then hashed with the SHA-256 hash function. Since the function returns a byte[] array, we much encode it to a string variable, so that it can be displayed on the webpage.
   
3. Do the same thing for 6 more java files under *spring-gumball/src/main/java/com/example/gumballmachine*. These files are named [*GumballMachine.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/gumballmachine/GumballMachine.java), [*HasQuarterState.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/gumballmachine/HasQuarterState.java), [*NoQuarterState.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/gumballmachine/NoQuarterState.java), [*SoldOutState.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/gumballmachine/SoldOutState.java), [*SoldState.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/gumballmachine/SoldState.java), [*State.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/java/com/example/gumballmachine/State.java), and their code can be found in their respective links. 

4. Now, we are going to copy and paste the webpage assets for the frontend of the *Spring Gumball* application. The [*gumball.html*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/resources/templates/gumball.html) file will be put under *spring-gumball/src/main/resources/templates*, the [*style.css*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/resources/static/images/styles.css) will be put under *spring-gumball/src/main/resources/static*, and the [*giant-gumball-machine.jpg*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/src/main/resources/static/images/giant-gumball-machine.jpg) will be put under *spring-gumball-v2/src/main/resources/static/images*. *gumball.html* was also editted to display the HMAC instead of the session ID on the bottom-left of the webpage.

5. Lastly, we are going to prepare the files needed to create the Docker image and deploy the application on Google Cloud. Copy and past the contents of [*build.gradle*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/build.gradle), [*Dockerfile*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/Dockerfile), [*deployment.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/deployment.yaml), [*ingress.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/ingress.yaml), [*pod.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/pod.yaml), and [*service.yaml*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/spring-gumball/service.yaml) from their respective links, and add these files to the *spring-gumball* folder. To upload your own Docker image to your own account, then you will need to change *shayannag* to your own Docker Hub username in the *pod.yaml* and *deployment.yaml* files.

### Deployment on Google Cloud
6. Before, deploying on Google Cloud, we are going to need to create a Docker image and upload it to Docker Hub. Run the following lines in a new terminal in Visual Studio Code to build the *Spring Gumball* application using Gradle:
   ```
   gradle clean
   gradle build
   gradle bootRun
   ```
   You can test if the applicationworks locally on your machine by going to *localhost:8080* on your browser of choice (I used Google Chrome.
   
7. To create the Docker image, run the following lines: 
   ```
   docker build -t spring-gumball-v2 .
   docker images
   ```
   
   To test that the Docker image was built correctly, you can run a new container with the built image on Docker Desktop with the following lines:
   ```
   docker run --name spring-gumball-v2 -td -p 80:8080 spring-gumball-v2	
   docker ps
   ```
   
8. Upload the Docker image to Docker Hub the following lines with your own Docker Hub credentials:
   ```
   docker login
   docker build -t $(account)/spring-gumball-v2 -t $(account)/spring-gumball-v2 .
   docker push $(account)/spring-gumball:v2 
   ```
   
9. Log into your [Google Cloud Console](https://cloud.google.com/gcp?utm_source=google&utm_medium=cpc&utm_campaign=na-US-test-all-en-dr-bkws-all-all-trial-e-dr-1009892&utm_content=text-ad-none-any-DEV_c-CRE_491349594127-ADGP_Desk%20%7C%20BKWS%20-%20EXA%20%7C%20Txt%20~%20Storage%20~%20Cloud%20Storage_Cloud-KWID_43700060017921803-kwd-6458750523&utm_term=KW_google%20cloud-ST_google%20cloud&gclid=CjwKCAjw-ZCKBhBkEiwAM4qfF6KsfYUTnzI8bfp8t8HuyR_7zjpOCiG7A-xfOuO0ObfQFU5_pFVerxoCGBwQAvD_BwE&gclsrc=aw.ds). In the left navigation bar, go to *Kubernetes Engine>Clusters>Create>Configure GKE Standard*. Rename the cluster to *spring-gumball-v2*, then on the left navigation bar go to *default-pool>Nodes* and change *Machine type* to *e2-small*. Finally, click *Create* at the bottom of the page.

10. After a few minutes, the cluster will be created. Click *Connect>Run in cloud shell* to run the terminal. Click on the three dots at the top of the terminal window, and upload *pod.yaml*, *deployment.yaml*, *ingress.yaml*, and *service.yaml*. Run the following lines in the terminal:
	```
	kubectl apply -f pod.yaml
	kubectl create -f deployment.yaml
	kubectl create -f ingress.yaml
	kubectl create -f service.yaml
	```

	To ensure that *spring-gumball-v2* and *spring-gumball-deployment* are running, you can navigate to *Workloads* on the left navigation bar. Similarly, *spring-gumball-services* can be viewed when you click on *Services & Ingress* on the same left navigation bar. Lastly, to navigate to *spring-gumball-ingress*, go to *Services & Ingress>Ingress*.
	
11. Run the application by going to *Services & Ingress*>*Ingress*, and clicking on the IP address until *Frontends*.

	Screenshots of the Spring Gumball V2 Deployment is shown below:
	![spring gumball v2 deployment 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20gumball%20v2%20deployment%201.png)
	![spring gumball v2 deployment 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20gumball%20v2%20deployment%202.png)

	Screenshots of the Spring Gumball V2 Ingress is shown below:
	![spring gumball v2 ingress 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20gumball%20v2%20ingress%201.png)
	![spring gumball v2 ingress 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20gumball%20v2%20ingress%202.png)

	Screenshots of the Spring Gumball V2 Services is shown below:
	![spring gumball v2 services 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20gumball%20v2%20services%201.png)
	![spring gumball v2 services 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20gumball%20v2%20services%202.png)

	Screenshots of the Spring Gumball V2 Application running on Google Cloud is shown below:
	![spring gumball v2 application 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20gumball%20v2%20application%201.png)
	![spring gumball v2 application 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab4/images/spring%20gumball%20v2%20application%202.png)
	
### Questions
**Do you see any errors that were observed in Spring Gumball (Version 1)? Why or Why Not?  Explain the technical details to support your observation.**

Yes, I ran into a Whitelabel Error similar to when I ran the Spring Gumball application on Google Cloud. This happened when I clicked on *Insert Quarter* then *Turn Crank*. The Spring Gumball application is stateful; since it is load balancing between 4 nodes, we need to direct the requests to the same backend node. To do so, we have to enable the session affinity for Google Cloud to configure the cluster to behave as described earlier. The type of session affinity setting I used was the generated cookie affinity setitng. The following steps can be used to configure the session affinity:

**Steps:**
1. Ensure that you are running the *Spring Gumball* application on Google Cloud. On the left navigation bar, go to *Services & Ingress*>*Ingress*. Scroll down until you see the *Load balancing resources* section, and click on the link next to *Load balancer*. 

2. Click on *EDIT* at the top of the page. Click on the *pencil icon* under *Backend services*. A menu will appear on the right side of the webpage. 

3. Scroll down until you see an *ADVANCED CONFIGURATIONS* drop-down menu, and click on it. Click on the *Session Affinity* drop-down menu, and select either *Client IP* or *Generated Cookie*. Click on *UPDATE* on the slide-out menu, then *UPDATE* once again under *Edit HTTP(S) load balancer*. 

4. Run the application again by going back to *Services & Ingress*>*Ingress*, and clicking on the IP address until *Frontends*. The *Spring Gumball* application should work properly as in the screenshots above.

