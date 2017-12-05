About
=====

Simple web application which creates short urls for a given target URL.
It uses CRC32 algorithm to generate path part of the short url.

Building
========

The project is based on Gradle and in order to build the artifact run

    ./gradlew build

You don't need to install Gradle beforehand - the gradlew wrapper will
do it for the project

Running
=======

On the local dev environment use the following Gradle command to run the app:

    ./gradlew bootRun

The application will start by default here: [http://localhost:8080]

Dependencies
------------

The application depends on MongoDB. Bu default it tries to connect
to the localhost instance. In order to customize use standard Spring Boot
configuration for MongoDB. Here is default connection:

    spring.data.mongodb.uri = mongodb://localhost/short_url

