FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/statistics-api-1.0.0.jar statistics-api-1.0.0.jar
ENTRYPOINT ["java","-jar","/statistics-api-1.0.0.jar"]