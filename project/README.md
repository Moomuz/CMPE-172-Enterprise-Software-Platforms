# GONG CHA
## from Team SpRRiNg
# ==========================================
## General Description

Team project for SJSU CMPE 172 Enterprise Software Fall 2021

## Navigation
 - [Design](##design)
 - [How to Run: Local](##how-to-run:-local)
 <!-- - [How to Run: Docker](##how-to-run:-docker) -->
 - [Reports](##reports)
 - *No fixed header-linking in README file, please use `Ctrl + F` to search.*

## Design
* The whole project is separated into four main division: three microservices and one backend RestAPI.
* The RestAPI is implemented and deployed On GKE beforehand for microservices to make use of.
* Each microservices is written within it's own folder and main running class, executing separately.
* However, all microservices shares the same MySQL database through the backend API for consistency.

# ==========================================

### 1. Cashier App

The Cashier App is used by the baristas to take in and process orders and their payments. This will call the CyberSource API to handle payments.

# ==========================================

### 2. Online Store

The Online Store manages customer data, such as user credentials, the user's card information, user's cart, and generating orders.

# ==========================================

### 3. Backoffice Help Desk

The Backoffice Help Desk is used by managers to change user's credentials.

# ==========================================

### 4. REST API

The REST API processes api calls to manage orders, customers, and cards, which will be called in the [Cashier App](###cashier-app), [Online Store](###online-store), and the [Backoffice Help Desk](###backoffice-help-desk).

# ==========================================

## How to Run: Local

NOTE: Clone the entire repository to a folder on your local machine before following these steps. The following steps runs the application locally. **ENSURE that Node.js is installed on your local machine.**

### Customer Microservice

1. Open the repository folder, navigate to `\fa21-172-sprring\customer\`, and open a terminal at this location.

2. Run the commands `gradle build` and `gradle bootRun` to run the customer microservice.

### Order Microservice

1. Open the repository folder, navigate to `\fa21-172-sprring\order\`, and open a terminal at this location.

2. Run the commands `gradle build` and `gradle bootRun` to run the order microservice.

## Payment Microservice

1. Open the repository folder, navigate to `\fa21-172-sprring\payment\`, and open a terminal at this location.

2. Run the commands `gradle build` and `gradle bootRun` to run the payment microservice.

### Front Office

1. Open the repository folder, navigate to `\fa21-172-sprring\frontend\`, and open a terminal at this location.

2. Run the command `npm install` (NOTE: you can skip warnings and npm audit).

3. In the terminal, navigate to `\fa21-172-sprring\frontend\my-app\`, and ru the command `npm install` (NOTE: you can skip warnings and npm audit).

4. In the terminal, run the command `npm start` to run the front-end app on the link [http://localhost:3000](http://localhost:3000)

NOTE: You will have 4 different terminals open

<!--
## How to Run: Docker

1. Follow the instructions in the [Payment Deployment readme file](payment/readme.md) to start up payment/.

2. Follow the instructions in the [Order Deployment readme file](order/readme.md) to start up order/.

3. Follow the instructions in the [Customer Deployment readme file](customer/readme.md) to start up customer/.

-->

# ==========================================

## Reports

Rozanna Abramtsis: [reports](weekly_journals/Rozanna/rozanna-reports.md)
Shayanna Gatchalian: [report 1](weekly_journals/Shayanna/Week-1-Report.md) [report 2](weekly_journals/Shayanna/Week-2-Report.md) [report 3](weekly_journals/Shayanna/Week-3-Report.md) [report 4](weekly_journals/Shayanna/Week-4-Report.md) [report 5](weekly_journals/Shayanna/Week-5-Report.md) [report 6](weekly_journals/Shayanna/Week-6-Report.md)
Nhu Nguyen: [reports](weekly_journals/Nhu/week1.md)
Ryan Tseng: [reports](weekly_journals/Ryan/Weekly-Report.md)
