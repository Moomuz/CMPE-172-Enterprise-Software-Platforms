# Customer Deployment

1. Open Docker Desktop.

2. Open a terminal and navigate to the `customer/` folder.

3. Create a new Docker network (if not created yet) by running the following commands:

```
docker network ls
docker network create --driver bridge gongcha-network
```

4. Create a Docker image by running the following command:

```
docker build -t customer-api .
```

5. Create the MySQL container by running the following command:

```
docker run -d --network gongcha-network --name customer-mysql -td -p 3308:3308 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0
```

6. Open the MySQL container's terminal on Docker (the CLI), and enter in the following in the terminal to create the `customer` database:

```
mysql --password
`cmpe172`
show databases;
create database customer;
show databases;
use customer;
```

7. Create the Payment container by running the following command:

```
docker run --network gongcha-network -e "MYSQL_HOST=mysql" --name customer-api -td -p 8090:8090 customer-api
```

8. In a browser, open [http://localhost/](http://localhost/) to view the webpage.

9. Push the Docker image to Docker Hub by running the following commands (replace $(account) with your Docker Hub username):

```
docker login
docker build -t $(account)/customer-api -t $(account)/customer-api .
docker push $(account)/customer-api
```

docker build -t zanagen/customer-api -t zanagen/customer-api .
docker push zanagen/customer-api