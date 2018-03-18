# Subscription API

One Paragraph of project description goes here

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

In order to build and run the application locally is required to have installed gradle, jdk8, docker and docker-compose.

### How to run the app

#### This command will build the project, build the docker images and start all of containers using docker-compose. The public api for creating subscriptions will be in the address http://localhost:8080/api/subscriptions
gradle run

### Running the tests

#### To perform some simple tests againts the exposed API, run the following command:
gradle test -p apiTest

### Stop the application

#### Once docker-compose is stopped with Ctrl+C, stop and remove the created containers using the following command:
gradle cleanContainers

### Description of microservices
#### These are the services. They are all deployed in different docker containers, and they communicate with each other using http protocol:

* API: This service exposes a public REST API providing a single endpoint to create subscriptions. A subscription is defined with the information of a user and the identifier of the newsletter to be subscribed.
* SUBSCRIPTION MANAGER SERVICE: This service acts as the business service layer which communicates the REST API Controller Service with the Persistence Service.
* PERSISTENCE SERVICE: This service stores the created subscriptions in a database.
* EVENT SERVICE: This service acts as a subscription broker. It receives the subscription information of the users who made explicit consent to receive newsletter notification by email. This service would maintain the subscriptions and expose an interface with a newsletter notification associated with a newsletterId. The service would send that notification and the list of the subscribed users to the email service.
* EMAIL SERVICE: This service receives a newsletter notification and a list of users and it send an email to every user with the new newsletter notification.

## Frameworks and libraries used:

* [Lombok]: Framework used to build data objects avoiding boilerplate code
* [Jackson]: Serialization framework used for autoconvert json messages to java objects and vice versa
* [Mockito]: Mocking and testing framework used to fake the behavior of the interface methods of some services
* [H2]: Database used to create an in-memory persistence layer
* [Swagger]: Framework used to document the Subscriptions REST API
* [Other spring boot dependencies]: Spring Data, Spring Security

## Authors

* Gabriel Barreras Sanz
