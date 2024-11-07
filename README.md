# Camunda-C8-Training

Project for learning and working with Camunda 8.
**This project uses the following versions:**
- Camunda 8.5
- Java 17
- Spring Zeebe Library 8.5.8 

## Getting started

## Template C8 Architecture

- [This is the architecture of Camunda 8](https://docs.camunda.io/docs/8.5/guides/)
  - See the image "Camunda Components & Architecture"

## Camunda 8 Docker Image Installation

The `\Docker` folder already contains the Docker compose and other files necessary to startup the environment. 

[This is the official Camunda documentation for the Docker image](https://docs.camunda.io/docs/8.5/self-managed/setup/deploy/local/docker-compose/)

The `docker-compose-web-modeler.yaml` file uses an image that is stored in one repository protected by username and password.
The username and password can be obtained by clients that have acquired the `self-managed` option

### How to run it

#### With the Camunda Web Modeler

- To start the environment: ``docker-compose -f docker-compose.yaml -f docker-compose-web-modeler.yaml up -d``
  - Note that the files .env and connector-secrets.txt are required
- To stop the environment: ``docker-compose -f docker-compose.yaml -f docker-compose-web-modeler.yaml stop``
- To delete all resources of the environment and start anew: ``docker-compose -f docker-compose.yaml -f docker-compose-web-modeler.yaml down``

#### Without the Camunda Web Modeler

- To start the environment: ``docker-compose -f docker-compose.yaml up -d``
  - Note that the files .env and connector-secrets.txt are required
- To stop the environment: ``docker-compose -f docker-compose.yaml stop``
- To delete all resources of the environment and start anew: ``docker-compose -f docker-compose.yaml down``

Links:
Log in with the user ``demo`` and password ``demo``

* Web Modeler: http://localhost:8070/
* Operate: http://localhost:8081/
* Tasklist: http://localhost:8082/
* Optimize: http://localhost:8083/
* Identity: http://localhost:8084

## Spring Zeebe Library

* We use this SDK library of Camunda: https://docs.camunda.io/docs/8.5/apis-tools/spring-zeebe-sdk/getting-started/
  ````angular2html
    <dependency>
        <groupId>io.camunda</groupId>
        <artifactId>spring-boot-starter-camunda-sdk</artifactId>
        <version>8.5.x</version>
    </dependency>
  ````
* This library was released in Autumn 2024 and is maintained by Camunda, it is part of the main github repository https://github.com/camunda/camunda/tree/main/clients/spring-boot-starter-camunda-sdk
* An older version of the above-mentioned library was maintained by the community, and it was archived by the time version 8.5 was released; the old library repository is https://github.com/camunda-community-hub/spring-zeebe, the repo contains valuable documentation for the specific Camunda version `8.5` we use in this project
* All the code examples using this library can be found in the module `zeebe-worker_spring-zeebe`

## Java Library (Base project for Spring Zeebe Library)

* You can also use this library https://docs.camunda.io/docs/8.5/apis-tools/java-client/ instead of the Spring Zeebe Library, the Spring Zeebe Library is in fact a wrapper around the Java Library
    ````angular2html
    <dependency>
      <groupId>io.camunda</groupId>
      <artifactId>zeebe-client-java</artifactId>
      <version>8.5.x</version>
    </dependency>
    ````
* This library is maintained by Camunda, and it is part of the main github repository https://github.com/camunda/camunda/tree/main/clients/java
* All the code examples using this library can be found in the module `zeebe-worker_java-client`

## Library for Testing

* There are 2 testing libraries for Camunda 8, they target the underlying Java installation, in other words, you choose the testing library based on the version of Java of your project
  * For projects with Java version >= 21:
    ````angular2html
    <dependency>
    <groupId>io.camunda</groupId>
    <artifactId>zeebe-process-test-extension</artifactId>
    <version>X.Y.Z</version>
    <scope>test</scope>
    </dependency>
    ````
  * For projects with Java version < 21 ***(This project uses this library)***:
    ````angular2html
    <dependency>
    <groupId>io.camunda</groupId>
    <artifactId>zeebe-process-test-extension-testcontainer</artifactId>
    <version>X.Y.Z</version>
    <scope>test</scope>
    </dependency>
    ````
* Official Camunda documentation: https://docs.camunda.io/docs/8.5/apis-tools/java-client/zeebe-process-test/ 
* Best pratices for testing: https://docs.camunda.io/docs/8.5/components/best-practices/development/testing-process-definitions/
