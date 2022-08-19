# Unit Testing workshop

# Intro

## Welcome

This Unit Testing Workshop is designed to get you familiar with unit testing in Java using popular open source tools, including JUnit 5, Mockito and AssertJ.

Starting with a complete and working application, you will be coached through the process of writing unit tests for it in your own IDE, until your tests cover the whole application. 

Many popular JUnit techniques and approaches are covered. When you complete the course you will...
- be able to understand and write good testable object-oriented code
- spot what needs to be tested
- know how to design a good unit test
- know how to make great assertions to ensure code doesn't break
- be confident in the libraries used

## Topics

The main things we will look at in this course are

### JUnit 5 as a test management and execution framework

- arrange / act / assert
- test lifecycle
- basic tests
- parameterised tests
- dynamic tests
- POJOs and service classes
- testing exceptions
- basic UI testing
- How to test sub classes
- How to use test utilities
- How to get coverage reports to track your testing progress
 
### AssertJ to make modern assertions in a fluent style

AssertJ is a great choice for making assertions. The built in assertions in JUnit 5, whilst more advanced than JUnit 4 are still very far behind what can be done in AssertJ. The original "assertThat" library Hamcrest has not been updated since 2019. Google Truth is very similar to AssertJ but has a smaller developer pool and a slower rate of enhancement. 

### Mockito to mock dependencies and inspect usage

Mockito is the leader in mocking frameworks, allowing objects and interfaces to be quickly simulated in test code, ensuring your unit test doesn't break due to failurs in code not under test. Since v3.4.0 it now supports mocking static classes, removing the main reason people needed to keep using the abandonded PowerMock project.

### Lombok library to reduce the amount of code you write (and thus need to test)

Lombok is a very popular library which provides low-level support for writing less code in your projects. Some of the features we use in this workshop are the getter/setter generation and the builder generation. This reduces the amount of code you need to write both in your POJOs and in the services that use them. This, of course, reduces the amount of tests you need to write.

## Pre-req

You need to have installed...

- JDK v8 or above
- Apache Maven
- A modern java IDE, such as Eclipse or IDEA (community edition is fine for this)

# The System

The Fantasy Railway System is an application that allows defining a railway network and running train services on it. It also allows adding passenger accounts and selling tickets to passengers who may be eligible for a discount.

Some additional notes on system behaviour:

- there is no distinction between weekdays and weekends
- services are setup according to a route and frequency
- children under the age of 13 travel free and require no ticket
- the system does not check tickets nor does it track passengers on their journey
- the system does not have different types of train, or any attributes of a train, such as capacity
- on startup the system will load the files in src/main/resources to bootstrap the data
- distances between the stations are measured in minutes
- every minute, the system checks what services have departed from which stations
- the term "service" is overloaded and may refer to a train service (not only a java service)
- the term "network" refers to the train network (not a computer network)

The system is written in a mutable object-oriented style, using both inheritance and composition where they make sense.

Main Services:

- **Accounts** : manages passengers in the system
- **Bookings** : allows purchasing of tickets
- **Network** : manages stations and the connections between them
- **Timetable** : manages train services running on the network

Each service has a corresponding text-based UI, allowing the user to interact with the system and perform various operations. This UI has been designed in the same way as Jim Weaver's pluralsigh course "Getting Started Unit Testing With JUnit 5", so if you have completed that course and reviewed his code, it will be familiar.

There is the ability to bulk-load data from files, making the process of setting up a railway network, services and passenger accounts easy. Suitable files of data are provided.

To keep things simple, there are no custom or checked exceptions in this project. IllegalStateException and IllegalArgumentException are used. These exceptions are also consistent with Google Guava Preconditions which are also used here.

As well as the various CRUD style operations, there are a few other interesting details in this system which you will dive into as you complete this workshop

- The railway network itself is represented by a Google Guava Value Graph.
- Tickets are priced by using a shortest path algorithm on the railway network.
- To aid time based testing, there is a "Now" class which centralizes the "Clock" instance allowing us to override in our tests.

Javadoc comments are provided throughout the codebase. Please read them to familiarise yourself with the system and how it fits together.

## How to Build

Once imported to your maven-capable Java IDE, the system will build without further instruction.

Various operations including building can also be done at the command line

- **compile** : ```mvn compile```
- **test** : ```mvn test```
- **package** : ```mvn package```

Please read the [Introduction to the Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html) for more details.

## How to Run

In an IDE, you can simply run the Main class without any parameters to start the system...

- **class** : org.fantasy.railway.Main
- **arguments** : none
- **vm args** : none

At the command line, you have two options, the first has a faster turnaround, the second requires ```mvn package``` to be run first.

- **unpackaged**: ```mvn compile exec:java -Dexec.mainClass=org.fantasy.railway.Main```
- **packaged** : ```java -jar target\railway-1.0-SNAPSHOT-jar-with-dependencies.jar```
- 
# About Unit Testing

## Pluralsight courses

- [JUnit 5 Fundamentals by Esteban Herrera](https://app.pluralsight.com/library/courses/junit-5-fundamentals)
- [Getting Started Unit Testing with JUnit 5 By Jim Weaver](https://app.pluralsight.com/library/courses/junit-5-unit-testing-getting-started)
- [Getting Started with Mockito 2 By Nicolae Caprarescu](https://app.pluralsight.com/library/courses/mockito-getting-started)

## Testing best practice

[Best Practices For Unit Testing In Java](https://www.baeldung.com/java-unit-testing-best-practices)

1. Have one test class per class under test. A 1:1 mapping is ideal.
1. Test classes should be named the same as the class under test with the word "Test" added to the end.
1. Test classes should be put into a package of the same name as the class under test, but in the **src/test/java** directory.
2. Test no more than one "unit" of code (a method) per test.
1. Use the **Arrange / Act / Assert** flow in your test code. Sometimes it makes sense to combine one or more of these, to improve readability and/or brevity.
1. Multiple tests per unit should be added when needed. Each unit may have different results (i.e. return a value or throw exception).
1. Test should be stateless and not rely on any other tests or state hangover. Reinitialize starting state before each test.
1. Tests should be executable in any order.
1. Use good, descriptive names for the test that capture the expected behaviour and input.
1. Use built-in features of JUnit, Mockito and AssertJ where possible.

## Basic JUnit testing

[A Guide to JUnit 5](https://www.baeldung.com/junit-5)

# Modules

## Module 1 : Basic Unit Testing

## Module 2 : Further Unit Testing

Static test utils
Expected/actual

Testing exceptions
1. Write PassengerTest



Verify

Spy

Mocking

Parameterised tests
1. EnumSource - ConcessionTest
2. ValueSource - RailwayUtilsTest
3. CsvSource - RailwayUtilsTest
4. Method source - GraphutilsTest

Dynamic tests
1. NetworkServiceImplTest

UI tests

Testing base class methods with base test class
1. Show BaseUITest and how it tests BaseUI and defines the contract


ArgumentCaptor

	1. Write AccountUITest - use verify, 

Testing main classes



Testing time
The "Now" class