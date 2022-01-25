# CMPE 172 - Lab #7 Notes

## CyberSource
**Steps:**

1. Create a CyberSource account at https://developer.cybersource.com/. Once your account is made, log into you dashboard at https://ebc2test.cybersource.com/ebc2/. 

2. On the left navigation bar, go to *payment configuration>key management>generate key>REST - shared secret key*, and download the .pem key file. To view the key details, go back to *key management* and click on the key you just created. Open the .pem file you just downloaded in Visual Studio Code, and cross check its information with the key details found on the CyberSource website.

## Spring Payments
**Steps:**

1. Download the starter files for lab 7 [*here*](https://github.com/paulnguyen/cmpe172/tree/main/labs/lab7), and open the *spring-cybersource* folder in Visual Studio Code. First, test that *spring-cybersource* is working by inputtng your key id, shared secret key, and merchant id into the application.properties file. Open a new terminal in Visual Studio Code, and run the program using the following commands:
   ```
   gradle build
   gradle bootRun
   ```

   If you run into bootRun errors, you may need to comment out the *@Tests* method in *SpringCybersourceApplicationTests.java* under *spring-cybersource/src/test/java/com/example*. If there are no errors, exit the program by entering *Ctrl+C* in the terminal, and move on to the next step.
   
2. Now, in a new Visual Studio Code window or the current one already open, open the *spring-payments* folder. Under *spring-payments/src/main/java/com/example*, create a new folder called *springcybersource*. Take all of the java files from *spring-cybersource* under *spring-cybersource/src/main/java/com/example/springcybersource* and copy them into the folder you just created. Then, delete the *SpringCybersourceApplication.java* file from the folder. 

3. Copy the code from *application.properties* under *spring-cybersource/src/main/resources* and paste it in addition to the code in the *application.properties* file under *spring-payments/src/main/resources*. The code will be different from person to person as you are using the unique shared key from CyberSource.

4. In the [*build.gradle*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/spring-payments/build.gradle) file under *spring-payments*, add the dependencies for *Spring JPA* and *H2 Database*, as shown in the respective link.

5. Under *spring-payments/src/main/java/com/example/springpayments*, create a new java file called [*PaymentsCommandRepository.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/spring-payments/src/main/java/com/example/springpayments/PaymentsCommandRepository.java). Copy and paste the code from the respective link.

6. Lastly, copy the code from [*PaymentsController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/spring-payments/src/main/java/com/example/springpayments/PaymentsController.java) and [*PaymentsCommand.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/spring-payments/src/main/java/com/example/springpayments/PaymentsCommand.java) and paste it into your files of the same name.

7. Now, you can finally run the program by opening a new terminal in Visual Studio Code and entering the following commands:
   ```
   gradle build
   gradle bootRun
   ```
   
   Here are the screenshots for validation in Spring Payments:
   ![spring validation 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/spring%20validation%201.png)
   ![spring validation 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/spring%20validation%202.png)
   ![spring validation 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/spring%20validation%203.png)
   
   Here are the screenshots for a successful transaction in Spring Payments:
   ![spring payments](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/spring%20payments.png)
   ![successful transaction 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/successful%20transaction%201.png)
   ![successful transaction 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/successful%20transaction%202.png)
   ![successful transaction 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/successful%20transaction%203.png)
   ![ebc dashboard 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/ebc%20dashboard%201.png)
   ![ebc dashboard 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab7/images/ebc%20dashboard%202.png)
   
## Discussion
1. Discussion of the Lombok, ThymeLeaf and Spring features used in this Lab.
   
   Lombok is used to provide basic template code for objects in Java. For example, the *@Getter* and *@Setter* annotations are used to make getter and setter methods for the private variables in *PaymentsCommand.java*. Thymeleaf works with the frontend model of the application and also has validation functionalities. This dependency will dictate what components will be deployed onto the webpage. The application also uses *@GetMapping* and *@PostMapping* annotations for HTTP requests and responses (e.g., when the form is submitted). It also includes functionality to save the form data to the H2 database.

2. Discuss why Jackson is needed and where it is used in the code for this Lab.
   
   Jackson is needed for this application to run, so that .json data can be sent to CyberSource and the transaction can be fulfilled through the microservice.
