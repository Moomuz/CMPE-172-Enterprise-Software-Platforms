# CMPE 172 - Lab #5 Notes

## Building REST Services with Spring
**Steps:**

   **Getting Started**
   
1. Go to https://start.spring.io/ and generate a new Spring Boot Project with the following parameters:
   - Project: Gradle Project
   - Language: Java Language (JDK 11)
   - Spring Boot Version: 2.4.11
   - Group: com.example
   - Artifact: spring-rest
   - Name: spring-rest
   - Package Name: come.example.spring-rest
   - Packaging: Jar
   - Dependencies:
     - Spring Web
     - Spring Data JPA
     - Spring HATEOAS
     - H2 Database
     
   Download the spring-rest zip folder, extract, and place into the lab5 folder.
   
   **The Story so Far…***

2. Open the folder in Visual Studio Code. Create 3 new java files called [*Employee.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/Employee.java), [*EmployeeRepository.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/EmployeeRepository.java), and [*LoadDatabase.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/LoadDatabase.java) under *spring-rest/src/main/java/com/example/springrest*, and copy and paste the code from their respective links.  

3. Open a new terminal in Visual Studio Code. Run the following lines, and take note of the output at the very end:
   ```
   gradle build
   gradle bootRun
   ```
  
   The end of your output should be similar to the following 2 lines:
   ```
   2021-10-15 09:52:36.602  INFO 18500 --- [           main] com.example.springrest.LoadDatabase      : Preloading Employee{id=1, name='Bilbo Baggins', role='burglar'}
   2021-10-15 09:52:36.607  INFO 18500 --- [           main] com.example.springrest.LoadDatabase      : Preloading Employee{id=2, name='Frodo Baggins', role='thief'}
    ```
   
**HTTP is the Platform**

4. Create 3 more java files called [*EmployeeController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/EmployeeController.java), [*EmployeeNotFoundException.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/EmployeeNotFoundException.java), and [*EmployeeNotFoundAdvice.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/EmployeeNotFoundAdvice.java) under *spring-rest/src/main/java/com/example/springrest*, and copy and paste the code from their respective links.

5. Build and run the application once again with the following llines:
   ```
   gradle clean
   gradle build
   gradle bootRun
   ```
  
   Now, to test the REST API, open a separate *Git Bash* terminal in Visual Studio Code. Run the following line to get information on the employees in the application:
   ```
   curl -v localhost:8080/employees
   ```

   You should get an output similar to the one below:
   ```
   *   Trying 127.0.0.1:8080...
   * Connected to localhost (127.0.0.1) port 8080 (#0)
   > GET /employees HTTP/1.1
   > Host: localhost:8080
   > User-Agent: curl/7.78.0
   > Accept: */*
   > 
   * Mark bundle as not supporting multiuse
   < HTTP/1.1 200
   < Content-Type: application/json        
   < Transfer-Encoding: chunked
   < Date: Fri, 15 Oct 2021 16:58:16 GMT
   <
   [{"id":1,"name":"Bilbo Baggins","role":"burglar"},{"id":2,"name":"Frodo Baggins","role":"thief"}]* Connection #0 to host localhost left intact
   ```

   Test the exception error by running the following line in the same terminal:
   ```
   curl -v localhost:8080/employees/99
   ```

   You should get an output similar to the one below:
   ```
   *   Trying 127.0.0.1:8080...
   * Connected to localhost (127.0.0.1) port 8080 (#0)
   > GET /employees/99 HTTP/1.1
   > Host: localhost:8080
   > User-Agent: curl/7.78.0
   > Accept: */*
   >
   * Mark bundle as not supporting multiuse
   < HTTP/1.1 404
   < Content-Type: text/plain;charset=UTF-8
   < Content-Length: 26
   < Date: Fri, 15 Oct 2021 17:04:06 GMT
   <
   Could not find employee 99* Connection #0 to host localhost left intact
   ```
   
6. You can create a new employee by using the template below in the *Git Bash* terminal:
   ```
   curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'
   ```
   
   It should output the following line immediately after, showing the Employee object:
   ```
   {"id":3,"name":"Samwise Gamgee","role":"gardener"}
   ```
   
   You can also edit the employee by using the following line in the same terminal:
   ```
   curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
   ```
   
   It should output the following line immediately after, showing the updated Employee object:
   ```
   {"id":3,"name":"Samwise Gamgee","role":"ring bearer"}
   ```
   
   The last REST API you can use is deleting the Employee object, using the following line in the same terminal: 
   ```
   curl -X DELETE localhost:8080/employees/3
   ```
   
   To check if the Employee is gone, look it up by its ID (in this case, the Employee we just created had an ID of 3). Input the following line in the terminal:
   ```
   curl localhost:8080/employees/3
   ```
   
   And it should return the following output to show the Employee has been deleted:
   ```
   Could not find employee 3
   ```
   
   **What makes something RESTful?**

7. Open *EmployeeController.java*, and replace the method starting with *Employee one(@PathVariable Long id)* to the updated method below:
   ```
   @GetMapping("/employees/{id}")
   EntityModel<Employee> one(@PathVariable Long id) {

     Employee employee = repository.findById(id) //
         .orElseThrow(() -> new EmployeeNotFoundException(id));

     return EntityModel.of(employee, //
         linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
         linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
   }
   ```
   
   This will give a better-formatted output to the *curl* commnd when outputting information of an Employee object. Rebuild the application and run the following line in a separate *Git Bash* terminal to get the output:
   ```
   curl -v localhost:8080/employees/1 | json_pp
   ```
   
   It should output the following line immediately after, showing the Employee object:
   ```
   *   Trying 127.0.0.1:8080...
     % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                    Dload  Upload   Total   Spent    Left  Speed
     0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
   > GET /employees/1 HTTP/1.1
   > Host: localhost:8080
   > User-Agent: curl/7.78.0
   > Accept: */*
   >
   * Mark bundle as not supporting multiuse
   < HTTP/1.1 200
   < Content-Type: application/hal+json
   < Transfer-Encoding: chunked
   < Date: Fri, 15 Oct 2021 18:49:41 GMT
   <
   { [172 bytes data]
   100   166    0   166    0     0    340      0 --:--:-- --:--:-- --:--:--   342
   * Connection #0 to host localhost left intact
   {
      "_links" : {
         "employees" : {
            "href" : "http://localhost:8080/employees"
         },
         "self" : {
            "href" : "http://localhost:8080/employees/1"
         }
      },
      "id" : 1,
      "name" : "Bilbo Baggins",
      "role" : "burglar"
   }
   ```
   
8. In the same *EmployeeController.java* file, replace the *List<Employee> all()* method with the following updated method: 
   ```
   @GetMapping("/employees")
   CollectionModel<EntityModel<Employee>> all() {

     List<EntityModel<Employee>> employees = repository.findAll().stream()
         .map(employee -> EntityModel.of(employee,
             linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
             linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
         .collect(Collectors.toList());

     return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
   }
   ```
   
   Rebuild the application and run the following line in a separate *Git Bash* terminal:
   ```
   curl -v localhost:8080/employees | json_pp
   ```
   The output should be similar to the following:
   ```
   *   Trying 127.0.0.1:8080...
     % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                    Dload  Upload   Total   Spent    Left  Speed
     0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
   > GET /employees HTTP/1.1
   > Host: localhost:8080
   > User-Agent: curl/7.78.0
   > Accept: */*
   >
   * Mark bundle as not supporting multiuse
   < HTTP/1.1 200 
   < Content-Type: application/hal+json
   < Transfer-Encoding: chunked
   < Date: Fri, 15 Oct 2021 19:06:49 GMT
   <
   { [437 bytes data]
   100   425    0   425    0     0   2390      0 --:--:-- --:--:-- --:--:--  2456
   * Connection #0 to host localhost left intact
   {
      "_embedded" : {
         "employeeList" : [
            {
               "_links" : {
                  "employees" : {
                     "href" : "http://localhost:8080/employees"
                  },
                  "self" : {
                     "href" : "http://localhost:8080/employees/1"
                  }
               },
               "id" : 1,
               "name" : "Bilbo Baggins",
               "role" : "burglar"
            },
            {
               "_links" : {
                  "employees" : {
                     "href" : "http://localhost:8080/employees"
                  },
                  "self" : {
                     "href" : "http://localhost:8080/employees/2"
                  }
               },
               "id" : 2,
               "name" : "Frodo Baggins",
               "role" : "thief"
            }
         ]
      },
      "_links" : {
         "self" : {
            "href" : "http://localhost:8080/employees"
         }
      }
   }
   ```
      
**Simplifying Link Creation**
      
9. Create a new java file called [*EmployeeModelAssembler.java*]() under *spring-rest/src/main/java/com/example/springrest*, and copy and paste the code from the link. 
     
10. Open *EmployeeController.java*. and replace the private variables and constructor with the following: 
    ```
    @RestController
    class EmployeeController {

      private final EmployeeRepository repository;

      private final EmployeeModelAssembler assembler;

      EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
      }

      ...

    }
    ```

    Then, replace the *EntityModel<Employee> one(@PathVariable Long id)* with the following updated method:
    ```
    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {

      Employee employee = repository.findById(id) //
          .orElseThrow(() -> new EmployeeNotFoundException(id));

      return assembler.toModel(employee);
    }
    ```

    And lastly, replace the *EntityModel<Employee> one(@PathVariable Long id)* with the following updated method:
    ```
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {

      List<EntityModel<Employee>> employees = repository.findAll().stream() //
          .map(assembler::toModel) //
          .collect(Collectors.toList());

      return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }
    ```

**Supporting changes to the API**

11. Now, replace the code in *Employee.java* with the code below: 
    ```
    package com.example.springrest;

    package payroll;

    import java.util.Objects;

    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.Id;

    @Entity
    class Employee {

      private @Id @GeneratedValue Long id;
      private String firstName;
      private String lastName;
      private String role;

      Employee() {}

      Employee(String firstName, String lastName, String role) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
      }

      public String getName() {
        return this.firstName + " " + this.lastName;
      }

      public void setName(String name) {
        String[] parts = name.split(" ");
        this.firstName = parts[0];
        this.lastName = parts[1];
      }

      public Long getId() {
        return this.id;
      }

      public String getFirstName() {
        return this.firstName;
      }

      public String getLastName() {
        return this.lastName;
      }

      public String getRole() {
        return this.role;
      }

      public void setId(Long id) {
        this.id = id;
      }

      public void setFirstName(String firstName) {
        this.firstName = firstName;
      }

      public void setLastName(String lastName) {
        this.lastName = lastName;
      }

      public void setRole(String role) {
        this.role = role;
      }

      @Override
      public boolean equals(Object o) {

        if (this == o)
          return true;
        if (!(o instanceof Employee))
          return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.firstName, employee.firstName)
            && Objects.equals(this.lastName, employee.lastName) && Objects.equals(this.role, employee.role);
      }

      @Override
      public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.role);
      }

      @Override
      public String toString() {
        return "Employee{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
            + '\'' + ", role='" + this.role + '\'' + '}';
      }
    }
    ```
  
  
12. Open *LoadDatabase.java*. Replace the lines in betwen *return args -> {};* with the following lines:
    ```
    log.info("Preloading " + repository.save(new Employee("Bilbo", "Baggins", "burglar")));
    log.info("Preloading " + repository.save(new Employee("Frodo", "Baggins", "thief")));
    ```
    
    When you redbuild and run the application and output details about the first employee, your output should look similar to this: 
    ```
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                     Dload  Upload   Total   Spent    Left  Speed
    100   207    0   207    0     0   1584      0 --:--:-- --:--:-- --:--:--  1604
    {
       "_links" : {
          "employees" : {
             "href" : "http://localhost:8080/employees"
          },
          "self" : {
             "href" : "http://localhost:8080/employees/1"
          }
       },
       "firstName" : "Bilbo",
       "id" : 1,
       "lastName" : "Baggins",
       "name" : "Bilbo Baggins",
       "role" : "burglar"
    }
    ```
  
    Two new fields are added for the first and last name of the Employee object. This is to show that it is best practice to add new fields to a data object, rather than delete and change corresponding fields. In this way, there is no downtime when updating a server, and clients do not have to update on their end either.

13. Open *EmployeeController.java* once again, and replace the method starting with *ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee)* with the updated method below:
    ```
    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

      EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

      return ResponseEntity //
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
          .body(entityModel);
    }
    ```
  
    Rebuild and rerun the application. Run the following line in a separate *Git Bash* terminal:
    ```
    curl -v -X POST localhost:8080/employees -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}' | json_pp
    ```
  
    Your output should be similar to the following:
    ```
    Note: Unnecessary use of -X or --request, POST is already inferred.
    *   Trying 127.0.0.1:8080...
      % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                     Dload  Upload   Total   Spent    Left  Speed
      0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
    > POST /employees HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.78.0
    > Accept: */*
    > Content-Type:application/json
    > Content-Length: 46
    >
    } [46 bytes data]
    * Mark bundle as not supporting multiuse
    < HTTP/1.1 201
    < Location: http://localhost:8080/employees/4
    < Content-Type: application/hal+json
    < Transfer-Encoding: chunked
    < Date: Fri, 15 Oct 2021 23:43:51 GMT
    < 
    { [221 bytes data]
    100   256    0   210  100    46    812    177 --:--:-- --:--:-- --:--:--  1024
    * Connection #0 to host localhost left intact
    {
       "_links" : {
          "employees" : {
             "href" : "http://localhost:8080/employees"
          },
          "self" : {
             "href" : "http://localhost:8080/employees/4"
          }
       },
       "firstName" : "Samwise",
       "id" : 4,
       "lastName" : "Gamgee",
       "name" : "Samwise Gamgee",
       "role" : "gardener"
    }
    ```
    
    The location header is now added to the output to allow hypermedia clients to use and interact with.
    
14. Now, replace the method starting with *Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id)* with the updated method below:
    ```
    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

      Employee updatedEmployee = repository.findById(id) //
          .map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
          }) //
          .orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
          });

      EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

      return ResponseEntity //
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
          .body(entityModel);
    }
    ```

    Rebuild and rerun the application. Run the following line in a separate *Git Bash* terminal:
    ```
    curl -v -X PUT localhost:8080/employees/3 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}' | json_pp
    ```
  
    Your output should be similar to the following:
    ```
    *   Trying 127.0.0.1:8080...
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                   Dload  Upload   Total   Spent    Left  Speed
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
    > PUT /employees/3 HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.78.0      
    > Accept: */*
    > Content-Type:application/json
    > Content-Length: 49
    >
    } [49 bytes data]   
    * Mark bundle as not supporting multiuse     
    < HTTP/1.1 201
    < Location: http://localhost:8080/employees/3
    < Content-Type: application/hal+json
    < Transfer-Encoding: chunked
    < Date: Fri, 15 Oct 2021 23:57:17 GMT        
    <
    { [219 bytes data]
    100   262    0   213  100    49    273     62 --:--:-- --:--:-- --:--:--   338
    * Connection #0 to host localhost left intact
    {
       "_links" : {
          "employees" : {
             "href" : "http://localhost:8080/employees"
          },
          "self" : {
             "href" : "http://localhost:8080/employees/3"
          }
       },
       "firstName" : "Samwise",
       "id" : 3,
       "lastName" : "Gamgee",
       "name" : "Samwise Gamgee",
       "role" : "ring bearer"
    }
    ```

15. Now, replace the method starting with *void deleteEmployee(@PathVariable Long id)* with the updated method below:
    ```
    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

      repository.deleteById(id);

      return ResponseEntity.noContent().build();
    }
    ```
  
    Rebuild and rerun the application. Run the following line in a separate *Git Bash* terminal:
    ```
    curl -v -X DELETE localhost:8080/employees/1 | json_pp
    ```
  
    Your output should be similar to the following:
    ```
    *   Trying 127.0.0.1:8080...
    * Connected to localhost (127.0.0.1) port 8080 (#0)
    > DELETE /employees/1 HTTP/1.1
    > Host: localhost:8080        
    > User-Agent: curl/7.78.0     
    > Accept: */*
    > 
    * Mark bundle as not supporting multiuse     
    < HTTP/1.1 204
    < Date: Sat, 16 Oct 2021 00:02:34 GMT        
    <
    * Connection #0 to host localhost left intact
    ```
       
    Moreover, when you try to print all of the employees, the employee with an ID of 1 will not be outputted anymore because it was successfully deleted.
       
**Building links into your REST API**
   
16. Now, we are going to add an order functionality in our application. Create 5 new java files called [*Order.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/Order.java), [*Status.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/Status.java), [*OrderRepository.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/OrderRepository.java), [*OrderController.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/OrderController.java), [*OrderModelAssembler.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/OrderModelAssembler.java), and [*OrderNotFoundException.java*](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/spring-rest/src/main/java/com/example/springrest/OrderNotFoundException.java) under *spring-rest/src/main/java/com/example/springrest*, and copy and paste the code from their respective links.
       
17. Open *OrderController.java* and insert the following *cancel* and *complete* methods
    ```
    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {

      Order order = orderRepository.findById(id) //
          .orElseThrow(() -> new OrderNotFoundException(id));

      if (order.getStatus() == Status.IN_PROGRESS) {
        order.setStatus(Status.CANCELLED);
        return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
      }

      return ResponseEntity //
          .status(HttpStatus.METHOD_NOT_ALLOWED) //
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
          .body(Problem.create() //
              .withTitle("Method not allowed") //
              .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
    }   
    ```

    ```
    @PutMapping("/orders/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {

      Order order = orderRepository.findById(id) //
          .orElseThrow(() -> new OrderNotFoundException(id));

      if (order.getStatus() == Status.IN_PROGRESS) {
        order.setStatus(Status.COMPLETED);
        return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
      }

      return ResponseEntity //
          .status(HttpStatus.METHOD_NOT_ALLOWED) //
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
          .body(Problem.create() //
              .withTitle("Method not allowed") //
              .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
    }
    ```

18. Open *LoadDatabase.java*, and replace all of the code with the following:
    ```
    package com.example.springrest;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    class LoadDatabase {

      private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

      @Bean
      CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {

        return args -> {
          employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
          employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));

          employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));


          orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
          orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

          orderRepository.findAll().forEach(order -> {
            log.info("Preloaded " + order);
          });

        };
      }
    }
    ```

19. To test out the application, build and run the application as we have done in the past. Then, in a separate *Git Bash* terminal, run the following line:
    ```
    curl -v http://localhost:8080/orders | json_pp
    ```

    You should have a similar output as the one below: 
    ```
    *   Trying 127.0.0.1:8080... 
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                   Dload  Upload   Total   Spent    Left  Speed
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
    > GET /orders HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.78.0
    > Accept: */*
    >
      0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Mark bundle as not supporting multiuse
    < HTTP/1.1 200 
    < Content-Type: application/hal+json
    < Transfer-Encoding: chunked
    < Date: Sat, 16 Oct 2021 02:19:06 GMT
    <
    { [550 bytes data]
    100   538    0   538    0     0   1134      0 --:--:-- --:--:-- --:--:--  1147
    * Connection #0 to host localhost left intact
    {
       "_embedded" : {
          "orderList" : [
             {
                "_links" : {
                   "orders" : {
                      "href" : "http://localhost:8080/orders"
                   },
                   "self" : {
                      "href" : "http://localhost:8080/orders/3"
                   }
                },
                "description" : "MacBook Pro",
                "id" : 3,
                "status" : "COMPLETED"
             },
             {
                "_links" : {
                   "cancel" : {
                      "href" : "http://localhost:8080/orders/4/cancel"
                   },
                   "complete" : {
                      "href" : "http://localhost:8080/orders/4/complete"
                   },
                   "orders" : {
                      "href" : "http://localhost:8080/orders"
                   },
                   "self" : {
                      "href" : "http://localhost:8080/orders/4"
                   }
                },
                "description" : "iPhone",
                "id" : 4,
                "status" : "IN_PROGRESS"
             }
          ]
       },
       "_links" : {
          "self" : {
             "href" : "http://localhost:8080/orders"
          }
       }
    }
    ```

    Take note of the deatils in the two orders. They take up ID numbers of 3 and 4 because they have to be unique in the same space as the Employees. Moreover, the Macbook Pro order has already been completed, so it only has link to all orders as well as to itself. The iPhone order is still in progress, so it has the additional links of cancel and complete. 

20. To further test the functionality of this application, you can test out the cancel and complete functions. Run the following line in the *Git Bash* terminal to test the cancel function:
    ```
    curl -v -X DELETE http://localhost:8080/orders/4/cancel | json_pp
    ```

    Your output should be similar to the HTTP 200 status below:
    ```
    *   Trying 127.0.0.1:8080...
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                   Dload  Upload   Total   Spent    Left  Speed
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
    > DELETE /orders/4/cancel HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.78.0
    > Accept: */*
    > 
    * Mark bundle as not supporting multiuse
    < HTTP/1.1 200
    < Content-Type: application/hal+json
    < Transfer-Encoding: chunked
    < Date: Sat, 16 Oct 2021 02:32:46 GMT
    <
    { [167 bytes data]
    100   161    0   161    0     0    867      0 --:--:-- --:--:-- --:--:--   889
    * Connection #0 to host localhost left intact
    {
       "_links" : {
          "orders" : {
             "href" : "http://localhost:8080/orders"
          },
          "self" : {
             "href" : "http://localhost:8080/orders/4"
          }
       },
       "description" : "iPhone",
       "id" : 4,
       "status" : "CANCELLED"
    }
    ```

    Run the same command and you will get an HTTP 405 Method Not Allowed response:
    ```
    *   Trying 127.0.0.1:8080...
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                   Dload  Upload   Total   Spent    Left  Speed
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0> DELETE /orders/4/cancel HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.78.0
    > Accept: */*
    >
    * Mark bundle as not supporting multiuse
    < HTTP/1.1 405
    < Content-Type: application/problem+json
    < Transfer-Encoding: chunked
    < Date: Sat, 16 Oct 2021 02:33:21 GMT
    <
    { [110 bytes data]
    100    99    0    99    0     0    547      0 --:--:-- --:--:-- --:--:--   559
    * Connection #0 to host localhost left intact
    {
       "detail" : "You can't cancel an order that is in the CANCELLED status",
       "title" : "Method not allowed"
    }
    ```

    Run the following line in the *Git Bash* terminal to test the complete function:
    ```
    curl -v -X PUT localhost:8080/orders/4/complete | json_pp
    ```

    Your output should be similar to the one below, as the application is attempting to delete an order with a cancelled status:
    ```
    *   Trying 127.0.0.1:8080...
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                   Dload  Upload   Total   Spent    Left  Speed
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
    > PUT /orders/4/complete HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.78.0
    > Accept: */*
    >
    * Mark bundle as not supporting multiuse
    < HTTP/1.1 405
    < Content-Type: application/problem+json
    < Transfer-Encoding: chunked
    < Date: Sat, 16 Oct 2021 02:33:53 GMT
    <
    { [112 bytes data]
    100   101    0   101    0     0    748      0 --:--:-- --:--:-- --:--:--   776
    * Connection #0 to host localhost left intact
    {
       "detail" : "You can't complete an order that is in the CANCELLED status",
       "title" : "Method not allowed"
    }
    ```

## Testing Spring REST on Postman
Ensure that the application is still running on localhost. Open your Postman application. On the left navigation bar, click on *Collections* and then the *+* button next to it. Create a new collection called *CMPE 172*. Click on *New* above the newly-made collection, and create an *HTTP Request*. From there, you can create and modify the *GET*, *POST*, *PUT*, and *DELETE* requests.
 
**Note: Testing on Postman was done after all the modifications in the Spring REST tutorial. Some outputs are different from the expected outputs because of these changes. I will explain the changes in the screenshots, where applicable.**
  
Get Employees:
![get employees 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20employees%201.png)
![get employees 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20employees%202.png)

Here, the output has 2 extra fields for the first and last name of the Employee object; these were added after the API was updated. The expected output would not have these fields.

Get Employee 1:
![get employee 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20employee%201.png)

Similar to the previous screenshots, this HTTP request returns the 2 extra fields for first and last name.

Get Employee 99:
![get employee 99](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20employee%2099.png)

Create Employee:
![create employee](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20employee%2099.png)

Similar to the previous screenshots, this HTTP request returns the 2 extra fields for first and last name.

Get Employee 5:
![get employee 5](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20employee%2099.png)

Similar to the previous screenshots, this HTTP request returns the 2 extra fields for first and last name. Moreover, this request is different in that it creates an Employee with an ID of 5. After creating links for the Order functionality of the application at the end of the tutorial, the two Order objects take up IDs 3 and 4. In this case, we instead create an Employee of ID 5 in order to utliize the same ID space. 

Update Employee 5:
![update employee 5](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20employee%205.png)

Similar to the previous screenshots, this HTTP request returns the 2 extra fields for first and last name.

Delete Employee 5:
![delete employee](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/delete%20employee.png)

Since there is no response, the HTTP request went through successfully.

Create Employee (New API):
![create employee (new api)](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/create%20employee%20(new%20api).png)

Update Employee (New API):
![update employee (new api)](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/update%20employee%20(new%20api).png)

Get Orders:
![get orders 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20orders%201.png)
![get orders 1](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/get%20orders%202.png)


Cancel Order:
![cancel order](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/cancel%20order.png)

A 405 error message was returned because we are attempting to cancel an already cancelled order.

Complete Order:
![complete order](https://github.com/nguyensjsu/cmpe172-moomuz/blob/main/labs/lab5/images/complete%20order.png)
