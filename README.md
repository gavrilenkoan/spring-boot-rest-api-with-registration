# Blog API

This is a blog REST API with the implementation of security using Spring Boot 3.0 and JSON Web Tokens (JWT).

## Tech Stack

**Server:** Java

**Database:** PostgreSQL

## Database diagram

[![db-diagram.png](https://i.postimg.cc/ncJY1H4J/db-diagram.png)](https://postimg.cc/grgZzbtS)

## Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* PostgreSQL

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* PostgreSQL

To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/gavrilenkoan/spring-boot-rest-api-with-registration.git`
* Navigate to the project directory: cd spring-boot-rest-api-with-registration
* Create database with name blog
* Add username and password to application.properties
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run

-> The application will be available at http://localhost:8080.