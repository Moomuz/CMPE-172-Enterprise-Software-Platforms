# CMPE 172 - Lab #9 Notes

## Spring RabbitMQ
**Steps:**

1. Download the starter files for lab 9 from https://github.com/paulnguyen/cmpe172/tree/main/labs/lab9/starter-code. There should be 3 folders. Open all 3 folders in Visual Studio Code.

2. Expand the *spring-rabbitmq* folder, and open the *build.gradle* file. Ensure that you have the dependency for *RabbitMQ*. Create 2 java files called [*Receiver.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq/src/main/java/com/example/springrabbitmq/Receiver.java) and [*Runner.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq/src/main/java/com/example/springrabbitmq/Runner.java) under *spring-rabbitmq/src/main/java/com/example/springrabbitmq*, and copy the code from their respective links. Add additional code for [*SpringRabbitmqApplication.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq/src/main/java/com/example/springrabbitmq/SpringRabbitmqApplication.java) as well.

3. Open [*application.properties*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq/src/main/resources/application.properties) under *spring-rabbitmq/src/main/resources*, and copy and paste the code from the respective link. Ensure that *spring.rabbitmq.host* is set to *localhost* for now.

4. Now, we will run a Docker container for the RabbitMQ console. Open Docker Desktop a new terminal in Visual Studio Code. Once the engine is running, run the following command in the terminal:
   ```
   docker network create --driver bridge springdemo
   ```
   It may pull the image for *rabbitmq:3-management* if you do not already have the image locally.
   
5. Then, run the following command to run the RabbitMQ container:
   ```
   docker run --name rabbitmq --network springdemo -p 8080:15672 -p 4369:4369 -p 5672:5672 -d rabbitmq:3-management
   ```
   Go to your Docker Desktop window, and make sure that the *rabbitmq* container is running.
   
   Screenshot of the RabbitMQ container on Docker Desktop:
   ![spring-rabbitmq docker](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq%20docker.png)
   
6. Now, open your preferred browser and navigate to *https://localhost:8080/*. The RabbitMQ login page should appear. Log in with username, *guest*, and password, *guest*. 

7. In your Visual Studio Code terminal, *cd* into the *spring-rabbitmq* folder and run the following lines: 
   ```
   gradle build
   gradle bootRun
   ```
   
   The output should include the following lines:
   ```
   Sending message... 
   Received <Hello from RabbitMQ!>
   ```
   
   Go back to the RabbitMQ console and navigate to the *Queues* tab. A new row called *spring-boot* should appear.
   
   Screenshots of Spring RabbitMQ are shown below:
   ![spring-rabbitmq 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq%201.png)
   ![spring-rabbitmq 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq%202.png)
   ![spring-rabbitmq 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq%203.png)
   
## Spring RabbitMQ Hello World
**Steps:**
1. Open the *application.properties* file under *spring-rabbitmq/src/main/resources*, and set *spring-rabbitmq.host* to *rabbitmq*. This is because the RabbitMQ console will no longer be on localhost from here.
 
2. Expand the *spring-rabbitmq-helloworld* folder in Visual Studio Code. Create 4 java files called [*Receiver.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-helloworld/src/main/java/com/example/springrabbitmqhelloworld/Receiver.java), [*Sender.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-helloworld/src/main/java/com/example/springrabbitmqhelloworld/Sender.java) , [*Config.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-helloworld/src/main/java/com/example/springrabbitmqhelloworld/Config.java), and [*RabbitAmqpTutorialsRunner.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-helloworld/src/main/java/com/example/springrabbitmqhelloworld/RabbitAmqpTutorialsRunner.java) under *spring-rabbitmq-helloworld/src/main/java/com/example/springrabbitmqhelloworld*, and copy the code from their respective links. Add additional code for [*SpringRabbitmqHelloworldApplication.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-helloworld/src/main/java/com/example/springrabbitmqhelloworld/SpringRabbitmqHelloworldApplication.java) as well.

3. Create 2 new files called [*application-dev.properties*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-helloworld/src/main/resources/application-dev.properties) and [*application-prod.properties*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-helloworld/src/main/resources/application-prod.properties) under *spring-rabbitmq-helloworld/src/main/resources*, and copy the code from their respective links. Add additional code to [*application.properties*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-helloworld/src/main/resources/application.properties) as well. 

4. In a new or current Visual Studio Code terminal, *cd* into the *spring-rabbitmq-helloworld* folder. Run the following lines to run the application:
   ```
   gradle build
   gradle bootRun
   ```
   
5. To run the *sender*, run the following line in the terminal:
   ```
   java -jar build/libs/spring-rabbitmq-helloworld-1.0.jar --spring.profiles.active=dev,hello,sender
   ```
   
   Go to the RabbitMQ console in your browser, and refresh the page. Go to the *Queues* page, and you should see that there are 10 ready messages in the *hello* row of the table. To further inspect the message, go to *hello>getMessages>set Ack mode = Nack message requeue tme>Get Message(s)*.
   
   Screenshots of the Spring RabbitMQ Hello World Sender are shown below:
   ![spring-rabbitmq-helloworld sender 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-helloworld%20sender%201.png)
   ![spring-rabbitmq-helloworld sender 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-helloworld%20sender%202.png)
   ![spring-rabbitmq-helloworld sender 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-helloworld%20sender%203.png)
   ![spring-rabbitmq-helloworld sender 4](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-helloworld%20sender%204.png)
   
6. Now, run the *receiver* using the following lines: 
   ```
   java -jar build/libs/spring-rabbitmq-helloworld-1.0.jar --spring.profiles.active=dev,hello,receiver
   ```
   
   Check the RabbitMQ console again. There should be 0 ready messages.
   
   Screenshots of the Spring RabbitMQ Hello World Receiver are shown below:
   ![spring-rabbitmq-helloworld receiver 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-helloworld%20receiver%201.png)
   ![spring-rabbitmq-helloworld receiver 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-helloworld%20receiver%202.png)
   
## Spring RabbitMQ Work Queues
**Steps:**
1. Expand the *sprig-rabbitmq-workers* folder. Ensure that the version in *build.gradle* is set to *1.0*. 

2. Copy over [*application-dev.properties*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-workers/src/main/resources/application-dev.properties), [*application-prod.properties*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-workers/src/main/resources/application-prod.properties), and [*application.properties*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-workers/src/main/resources/application.properties) from the *Spring RabbitMQ Hello World* portion from this lab to *spring-rabbitmq-workers/src/main/resources*. 

3. Do the same for [*RabbitAmqpTutorialsRunner.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-workers/src/main/java/com/example/springrabbitmqworkers/RabbitAmqpTutorialsRunner.java) and [*SpringRabbitmqWorkersApplication.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-workers/src/main/java/com/example/springrabbitmqworkers/SpringRabbitmqWorkersApplication.java), and put these under *spring-rabbitmq-workers/src/main/java/com/example/springrabbitmqworkers*. Make sure to change the class name of *SpringRabbitmqHelloworldApplication* to *SpringRabbitmqWorkersApplication* in the *SpringRabbitmqWorkersApplication.java* file. 

4. In the same *spring-rabbitmq-workers/src/main/java/com/example/springrabbitmqworkers*, create 3 java files called [*Config.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-workers/src/main/java/com/example/springrabbitmqworkers/Config.java), [*Receiver.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-workers/src/main/java/com/example/springrabbitmqworkers/Receiver.java), and [*Sender.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/spring-rabbitmq-workers/src/main/java/com/example/springrabbitmqworkers/Sender.java), and copy the code their respective links.

5. In a new or current Visual Studio Code terminal, *cd* into the *spring-rabbitmq-workers* folder. Run the following lines to run the application:
   ```
   gradle build
   gradle bootRun
   ```
   
6. To run the *sender*, run the following line in the terminal:
   ```
   java -jar build/libs/spring-rabbitmq-workers-1.0.jar --spring.profiles.active=dev,work-queues,sender
   ```

   Go to the RabbitMQ console in your browser, and refresh the page. Go to the *Queues* page, and you should see that there are 10 ready messages in the *hello* row of the table (it uses the same queue from the *Spring RabbitMQ Hello World* portion of this lab. To further inspect the message, go to *hello>getMessages>set Ack mode = Nack message requeue tme>Get Message(s)*.
   
   Screenshots of the Spring RabbitMQ Workers Sender are shown below:
   ![spring-rabbitmq-workers sender 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-workers%20sender%201.png)
   ![spring-rabbitmq-workers sender 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-workers%20sender%202.png)
   ![spring-rabbitmq-workers sender 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-workers%20sender%203.png)
   ![spring-rabbitmq-workers sender 4](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-workers%20sender%204.png)
   
7. Now, run the *receiver* using the following lines: 
   ```
   java -jar build/libs/spring-rabbitmq-workers-1.0.jar --spring.profiles.active=dev,work-queues,receiver
   ```
   
   Check the RabbitMQ console again. There should be 0 ready messages.
   
   Screenshots of the Spring RabbitMQ Workers Receiver are shown below:
   ![spring-rabbitmq-workers receiver 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-workers%20receiver%201.png)
   ![spring-rabbitmq-workers receiver 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-workers%20receiver%202.png)
   ![spring-rabbitmq-workers receiver 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-workers%20receiver%203.png)
   ![spring-rabbitmq-workers receiver 4](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab9/images/spring-rabbitmq-workers%20receiver%204.png)
   
   When trying to run the *receiver* on 2 separate occasions, the application failed to receive all 10 messages in one go, shown in the third screenshot. This may have been because my computer was too slow to process all of the messages within the 10000 milisecond time window. All 10 messages were received, however, when I ran the same *receiver* program subsequently after.
   
## Discussion
**1. A discussion of what Spring Profiles are and how they can be used in your Team Project.**
     Applications can run in different environments such as in developer mode, quality assurance testing, and production mode deployed to the market. Spring Profiles are meant to automatically create the different environments for the different stages of production and deployment, allowing us to not have to manually implement the code for different environments. We can use Spring Profiles in our project the same way enterprises do, but on a much smaller scale. For the main part, we can use Spring Profiles for developer and testing environments for our application, especially given the time constraint at this point in the semester. 

**2. A discussion of how RabbitMQ can be used in your Team Project (i.e. what's the use case?).**
     RabbitMQ acts as a message queue manager between separate services. In our project, we can use RabbitMQ to queue messages to send the form information to CYberSource when a customer purchases a products from our website.
