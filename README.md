# User API
[![Build Status](https://travis-ci.org/blacar/userapi.svg?branch=master)](https://travis-ci.org/blacar/userapi)

A rest API exposing User resources through standard REST endpoints.

This is using Spring MVC running on a spring-boot application.
This is using in-memory Mongo database since seems enough for the complexity and expected workload.

### Features:

- CI made with travis
- Code quality -> Using checkstyle, findbugs, and JaCoCo
- Allow to build docker image for local testing

### TODO:
- Remove embedded mongo in prod release
- Better API documentation
- Smaller container using "alpine" or other approaches
- Better warning suppressions
- Publish into dockerHub
- Deployment into cloud from travis
- JavaDoc

### Why just In-Memory DB?

Seems enough for the purpose.
Work to have a real one is not complex but time consuming.

### Why MongoDb?

Although not a big fan of MongoDb seemed good for the purpose.
I also considered CouchDb in combination with Spring Data CouchBase
or H2 in combination with jOOQ, or Spring Data JDBC.


### Usage:

Build:
**mvn clean install [docker:build]**

Option 1:

java -jar ./target/userapi-0.0.1-SNAPSHOT.jar

Option 2: With memcache using docker 

START: docker-compose up -d

STOP: docker-compose down

### API Usage

**Get All Users:**
curl -X GET http://127.0.0.1:8080/user

**Create New User:**
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"user1\", \"email\":\"user1@test.com\", \"phone\":\"0034666666666\"}" http://127.0.0.1:8080/user

**Update User Name:**
curl -X PUT -H "Content-Type: application/json" -d "{\"name\":\"user1\", \"email\":\"user1@test.com\", \"phone\":\"0034666666666\"}" http://127.0.0.1:8080/user/{user_id}



 

