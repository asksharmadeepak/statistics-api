# statistics-api

Description :

    It's Spring boot based microservice which for comsume and return statistics row data. 

Highlights -

    1. Standalone api using spring boot.
    2. Developed to make as production version as possible.
    3. Covered wit Junit test cases.
    4. Added Docker support , with its instruction to run.
    5. Global Exception handling.
    6. Added swagger for api documentation purpose.
    9. Confugurable validation for raw input in env variable.
    10. Added swagger for api documentation purpose.
    11. Used Future for asynchronous communication for event and stats.
    
Using Tools & Technologies - 

`   - Spring Boot- 2.4.3
    - REST API Standard
    - Lombok
    - Docker 
    - Logging
    - Asyncronous Communication (Futures)
    - Swagger
    - Java 8
`
    
Running instruction for Docker - 

    1.  $ mvn clean package -> generate the jar file
    2.  $ java -jar target/statistics-api-0.0.1-SNAPSHOT.jar  ->  try to run the file
    3.  Create file called docker file -
            FROM openjdk:8-jdk-alpine
            MAINTAINER baeldung.com
            COPY target/statistics-api-0.0.1-SNAPSHOT.jar statistics-api-0.0.1-SNAPSHOT.jar
            ENTRYPOINT ["java","-jar","/statistics-api-0.0.1-SNAPSHOT.jar"]
    
    4.  To create an image from our Dockerfile, we have to run ‘docker build', like before:
            $ docker build --tag=statistics-api:latest .
    
    5. We are able to run the container from our image:
            $ docker run -p8887:8888 exchange-rate:latest

Instruction to run :

    Execute command to run the project: 
        mvn spring-boot:run
    
    App will start respond on below routes: 
    
        http://localhost:9090/event  - POST  
        http://localhost:9090/stats  - GET
        
    Api Docs (Swagger) : 
        http://localhost:9090/v2/api-docs
        http://localhost:9090/swagger-ui.html