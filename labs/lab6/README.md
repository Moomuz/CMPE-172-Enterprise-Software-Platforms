# CMPE 172 - Lab #6 Notes

## Spring JDBC
**Steps:**
   
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.12
   - Group: com.example
   - Artifact: spring-jdbc
   - Name: spring-jdbc
   - Package Name: come.example.spring-jdbc
   - Packaging: Jar
   - Dependencies:
     - Spring Web
     - JDBC API
     - H2 Database
     
   Download the spring-jdbc zip folder, extract, and place into the lab6 folder.
   
2. Open the folder in Visual Studio Code. Create 2 new java files called [*Customer.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/spring-jdbc/src/main/java/com/example/springjdbc/Customer.java) and [*SpringJdbcApplication.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/spring-jdbc/src/main/java/com/example/springjdbc/SpringJdbcApplication.java) under *spring-rest/src/main/java/com/example/springjdbc*, and copy and paste the code from their respective links.

3. Open a new terminal in Visual Studio Code, and enter the following lines to run the application:
   ```
   gradle build
   gradle bootRun
   ```
   
   Here are the screenshots for Spring JDBC:
   ![spring jdbc 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/spring%20jdbc%201.png)
   ![spring jdbc 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/spring%20jdbc%202.png)
   ![spring jdbc 3](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/spring%20jdbc%203.png)
   
## Spring MySQL
**Steps:**
   
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.12
   - Group: com.example
   - Artifact: spring-sql
   - Name: spring-sql
   - Package Name: come.example.spring-sql
   - Packaging: Jar
   - Dependencies:
     - Spring Web
     - Spring Data JPA
     - MySQL Driver

2. Open the *MySQL Workbench* application, and use it as a *root* user. Run the following MySQL queries to create a new database called *db_example* and user called *springuser%*, and grant all privileges to onto the new user.
   ```
   create database db_example;
   create user 'springuser'@'%' identified by 'ThePassword';
   grant all on db_example.* to 'springuser'@'%';
   ```
   
   An output should render at the bottom of the application window.
   
3. Open the *spring-mysql* folder in Visual Studio Code. Then, open the [*application.properties*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/spring-mysql/src/main/resources/application.properties) under *spring-mysql/src/main/resources*. Copy and paste the code from the respective link. 

4. Create 3 new files under *spring-mysql/src/main/java/com/example/springmysql* called [*User.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/spring-mysql/src/main/java/com/example/springmysql/User.java), [*UserRepository.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/spring-mysql/src/main/java/com/example/springmysql/UserRepository.java), and [*MainController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/spring-mysql/src/main/java/com/example/springmysql/MainController.java). Copy and paste the contents from their respective links.

5. Lastly, open [*SpringMysqlApplication.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/spring-mysql/src/main/java/com/example/springmysql/SpringMysqlApplication.java), and copy and paste the contents from the link.

6. Now, open a new terminal in Visual Studio Code, and run the following lines to run the application:

   ```
   gradle build
   gradle bootRun
   ```

   To test the application, open a separate *Git Bash* terminal in Visual Studio Code. When you run the following line: 

   ```
   Curl localhost:8080/demo/add -d name=First -d email=someemail@someemailprovider.com
   ```

   A new user is created and the terminal will output *Saved*.

   The following command line shows all users saved:

   ```
   curl ‘localhost:8080/demo/all’
   ```

   And it will output the following:

   ```
   [{“id”:1,”name”:”First”,”email”:”someemail@someemailprovider.com}]
   ```

7. Right now the application is prone is SQL injection attacks because we granted *springuser%* with all the same privileges as a root user. So, to fix this, open the *MySQL Workbench* application once again and run the following queries:
   ```
   revoke all on db_example.* from 'springuser'@'%';
   grant select, insert, delete, update on db_example.* to 'springuser'@'%';
   ```

   This way, *springuser%” can only edit data in existing tables and databases and cannot create or delete tables and databases. 
   Rerun the *spring-mysql* application again to test the new changes.

   Screenshots of the *spring-mysql* application is shown below:
   ![spring mysql 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/spring%20mysql%201.png)
   ![spring mysql 2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/spring%20mysql%202.png)


## SQL vs MongoDB
**Steps:**
1. First, we need to set up the *bios* database using the MongoDB shell. Create a new javascript file called [*bios.js*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/mongodb%20files/bios.js) in Visual Studio Code. Copy and paste the contents from the respective link. This will be the data we will put into the database. 

2. Open a new terminal in Visual Studio Code. To open the MongoDB shell, type *mongo* and press enter. Enter the following commands to create the *bios* database and insert the data from *bios.js*.
   ```
   use bios
   mongo localhost:27017/bios bios.js
   ```
3. Now, you may enter the 10 queries in the MongoDB shell. These queries can be found here in [*mongo-queries.md*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/mongodb%20files/mongo-queries.md).

Below are the converted MongoDB queries along with screenshots of their outputs:
1. Count of Records/Documents
   ```
   db.bios.find().count()
   ```
   
2. Count of Records/Documents
   ```
   db.bios.find({
       birth: {
           $lt: ISODate("1950-01-01T00:00:00.000Z")
       }
   }).pretty()
   ```
   ![mongodb query 1+2](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/mongodb%20query%201%2B2.png)
   
3. Get a unique listing of all the awards (in db/collection) granted
   ```
   db.bios.distinct("awards.award")
   ```
   
4. Get sorted listing of all first names(ascending order)
   ```
   db.bios.find({}, {"name.first": 1, _id: 0}).sort({"name.first": 1})
   ```
   ![mongodb query 3+4](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/mongodb%20query%203%2B4.png)

5. Get sorted listing of all first names(descending order)
   ```
   db.bios.find({}, {"name.first": 1, _id: 0}).sort({"name.first": -1})
   ```
   
6. Count number of bios that do not have an award yet
   ```
   db.bios.find({"awards": {$exists: false}}).count()
   ```
   ![mongodb query 5+6](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/mongodb%20query%205%2B6.png)

7. Display system ID (primary key) for bio in query #6
   ```
   db.bios.find({"awards": {$exists: false}}, {_id: 1})
   ```
   ![mongodb query 7+10](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/mongodb%20query%207%2B10.png)

8. Display names (first and last) along with awards and contributions from bios with 1 contribution AND 2 awards
   ```
   **was not able to fully answer this question**
   // count number of awards
    b.bios.aggregate(
       { $unwind: "$awards" },
       { $group: {
           _id: {$concat: ["$name.first", " ", "$name.last"]},
           totalawards: { $sum: 1 }
       }}
   )

   // count number of contribs
   db.bios.aggregate([
       {$project: { 
           totalcontribs: { $size: "$contribs" }
       }}
   ])
   ```

9. Display names (first and last) along with awards and contributions from bios with 1 contribution OR 2 awards
   ```
   **was not able to fully answer this question**
   // count number of awards
    b.bios.aggregate(
       { $unwind: "$awards" },
       { $group: {
           _id: {$concat: ["$name.first", " ", "$name.last"]},
           totalawards: { $sum: 1 }
       }}
   )

   // count number of contribs
   db.bios.aggregate([
       {$project: { 
           totalcontribs: { $size: "$contribs" }
       }}
   ])
   ```

10. List all awards for a bio
    ```
    db.bios.find(
        { },
        {
            name: {$concat: ["$name.first", " ", "$name.last"]},
            awards: {award: 1}, _id: 0
        }
    ).limit(1).pretty()
    ```
    ![mongodb query 7+10](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab6/images/mongodb%20query%207%2B10.png)
