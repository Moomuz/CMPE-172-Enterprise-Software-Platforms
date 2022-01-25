# Order Deployment

1. Open Docker Desktop.

2. Open a terminal and navigate to the `order/` folder.

3. Create a new Docker network (if not created yet) by running the following commands:

```
docker network ls
docker network create --driver bridge gongcha-network
```

4. Create a Docker image by running the following command:

```
docker build -t order-api .
```

5. Create the MySQL container by running the following command:

```
docker run -d --network gongcha-network --name order-mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0
```

6. Open the MySQL container's terminal on Docker (the CLI), and enter in the following in the terminal to create the `orders` database:

```
mysql --password
`cmpe172`
show databases;
create database orders;
show databases;
use orders;
```

7. Create the Order container by running the following command:

```
docker run --network gongcha-network -e "MYSQL_HOST=mysql" --name order-api -td -p 8080:8080 order-api
```

8. In a browser, open [http://localhost/](http://localhost/) to view the webpage.

9. Push the Docker image to Docker Hub by running the following commands (replace $(account) with your Docker Hub username):

```
docker login
docker build -t $(account)/payment-api -t $(account)/payment-api .
docker push $(account)/payment-api
```

docker build -t zanagen/order-api -t zanagen/order-api .
docker push zanagen/order-api