# CMPE 172 Final Part 2

**Steps:**
1. Before deploying to Docker, make sure that the application can run locally using Gradle. Open the *spring-gumball-v3* folder in Visual Studio Code. In *application.properties* under *spring-gumball-v3/src/main/resources*, ensure that the following lines are commented out (these lines are used for deploying with the MySQL database, so we will not need them for now): 
   ```
   # using mysql database
   spring.jpa.hibernate.ddl-auto=update
   spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/cmpe172
   spring.datasource.username=root
   spring.datasource.password=cmpe172
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
   ```

2. Open a new terminal in Visual Studio Code. Run the following lines:
   ```
   gradle build -x test
   gradle bootRun
   ```

3. Open your browser of choice (I used Google Chrome), and go to *localhost:8080*. The *Spring Gumball* application should show up. You can click on the buttons to insert quarters and turn cranks to get gumballs.

4. Now that we know the application is implemented, we can now deploy the application locally on Docker. Uncomment the lines you commented out earlier, and comment out the following lines (we will now use the lines needed for the MySQL database):
   ```
   # using h2 database
   spring.h2.console.enabled=true
   spring.datasource.url=jdbc:h2:mem:cmpe172
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=password
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   ```

5. Check your *build.gradle* file and ensure that the version has been set to blank (this will ensure that the JAR file will be called *spring-gumball.jar*) In the same terminal, run the following lines to create the new JAR file:
   ```
   gradle clean
   gradle build -x test
   gradle bootRun
   ```

6. With the JAR file we just built, we will now build the Docker image. Check your *Dockerfile* and ensure that the JAR file is set to *spring-gumball* before doing so. Open a new terminal in Visual Studio Code, and run the following line:
   ```
   docker build -t spring-gumball-3.0 .
   ```

   Then, check that the image has been successfully built by running the following line:
   ```
   docker images
   ```
   The *spring-gumball* image should show up. Also, check that you have the *mysql* image pulled from Docker Hub. If not, run the following line:
   ```
   docker pull mysql:8.0
   ```

7. Ensure that you have the *docker-compose.yaml* file under the *spring-gumball* folder with the following content: 
   ```
   version: "3"

   services:
     mysql:
       image: mysql:8.0
       volumes:
         - /sql/sql
       networks:
         - network
       ports:
         - 3306
       environment:
         MYSQL_ROOT_PASSWORD: "cmpe172"
       restart: always
     gumball:
       image: spring-gumball
       volumes:
         - /sql:/sql
       networks:
         - network   
       ports:
         - 8080
       environment:
         MYSQL_HOST: "mysql"
       restart: always     
     lb:
       image: eeacms/haproxy
       depends_on:
       - gumball
       ports:
       - "80:5000"
       - "1936:1936"
       environment:
         BACKENDS: "gumball"
         BACKENDS_PORT: "8080"
         DNS_ENABLED: "true"
         COOKIES_ENABLED: "false"
         LOG_LEVEL: "info"
       networks:
         - network

   volumes:
     schemas:
       external: false

   networks:
     network:
       driver: bridge
   ```

8. Deploy the MySQl database, loadbalancer, and 2 spring-gumball Docker containers by running the following line in the same terminal you have been using for Docker commands:
   ```
   docker-compose up --scale gumball=2 -d
   ```

9. Go back to *Docker Desktop*. Once the 4 containers are fulling running, open the details about *spring-gumball_mysql_1* container by clicking on it. Then, open the CLI by clicking on the button that looks like >_ on the top-right of the window. Once the terminal window pops up, run the following lines one-by-one:
   ```
   mysql --password
   cmpe172
   create database cmpe172;
   show databases;
   use cmpe172;
   ```

10. Now, the application should be fully running. Go back to the *Containers* page on *Docker Desktop*. Hover over the section of either *spring-gumball_gumball_1* or *spring-gumball_gumball_2*. Open the application on the browser by clicking on the button that has an arrow coming out of a square. It should redirect you to your default browser, with the URL set to *localhost:{some port number}*. Now, you have the *spring-gumball* application successfully deployed on 2 containers along with a loadbalancer and connected to MySQL.

