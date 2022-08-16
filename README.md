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

Main Services:
- **Accounts** : allows adding passengers to the system
- 



# Assignment

## 