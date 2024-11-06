# Camunda-C8-Training

Project for learning and working with Camunda 8.
**This project uses the version 8.5**

## Getting started

## Template C8 Architecture

- [This is the architecture of Camunda 8](https://docs.camunda.io/docs/8.5/guides/)
  - See the image "Camunda Components & Architecture"

## Camunda 8 Docker Image Installation

The `\Docker` folder already contains the Docker compose and other files necessary to startup the environment. 

[This is the official Camunda documentation for the Docker image](https://docs.camunda.io/docs/8.5/self-managed/setup/deploy/local/docker-compose/)

## Usage

- To start the environment: ``docker-compose -f docker-compose.yaml -f docker-compose-web-modeler.yaml up -d``
  - Note that the files .env and connector-secrets.txt are required
- To stop the environment: ``docker-compose -f docker-compose.yaml -f docker-compose-web-modeler.yaml stop``
- To delete all resources of the environment and start anew: ``docker-compose -f docker-compose.yaml -f docker-compose-web-modeler.yaml down``

Links:
Log in with the user ``demo`` and password ``demo``

* Web Modeler: http://localhost:8070/
* Operate: http://localhost:8081/
* Tasklist: http://localhost:8082/
* Optimize: http://localhost:8083/
* Identity: http://localhost:8084

## Spring Zeebe Library

* We use this SDK library of Camunda: https://docs.camunda.io/docs/8.5/apis-tools/spring-zeebe-sdk/getting-started/

## Base Java Library for Zeebe

* You can also use this library https://docs.camunda.io/docs/8.5/apis-tools/java-client/ instead of the Spring Zeebe Library, the Spring Zeebe Library is in fact a wrapper around the Java Library

