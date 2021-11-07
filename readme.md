[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

<div align="center">

![Logo](./logo.png)

**Simple social media web application made for learning purposes.**
</div>

## Live version

Check out live version right now on: https://my-thought.netlify.app/

## Tech stack

### Backend
* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* Json Web Token (with Java JWT)   
* H2 Database
* Lombok
* JUnit

### Frontend

* React
* React Router
* Material-UI

## Features

* Creating and signing in to account
* Creating "thoughts" (at most 255 character long texts identified using tags, made by other users)
* Browsing the latest thoughts 
* Liking and commenting thoughts
* Browsing thoughts of chosen user
* Browsing thoughts with chosen tag
* Getting list of most popular tags

## Running project on your machine

* First you should open a terminal window inside the project root folder and then run the following command
  ```console
  mvn spring-boot:run
* Next you have to locate the .env.sample file in frontend directory. Now you should change its name to ".env" and set
  the
  *REACT_APP_API_BASE* accordingly to your working environment needs. For most cases it should be just set
  to `"http://localhost:8080/api/"`.
* At the end you have to run following command inside *frontend* directory.
  ```console
  npm start

Now the project should be working on your local machine.