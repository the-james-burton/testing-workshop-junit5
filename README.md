# Unit Testing workshop

# Intro

## Welcome

This Unit Testing Workshop is designed to get you familiar with unit testing in Java using popular open source tools, including JUnit 5, Mockito and AssertJ.

Starting with a complete and working application, you will be coached through the process of writing unit tests for it in your own IDE, until your tests cover the whole application. 

## Learning Objectives

Many popular JUnit techniques and approaches are covered. When you complete the course you will...

- Be able to understand what makes object-oriented java code testable
- Spot what needs to be tested
- Write JUnit test cases
- Know how to design a good unit test
- Know how to make great assertions to ensure code doesn't break
- Be aware of some of the more advanced features of JUnit 5
- Use Mockito to better isolate your tests
- Use AssertJ to make better assertions in your tests

## Topics

The main things we will look at in this course are...

### [JUnit 5](https://junit.org/junit5/docs/current/user-guide/) as a test management and execution framework

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
 
### [AssertJ](https://assertj.github.io/doc/) to make modern assertions in a fluent style

AssertJ is a great choice for making assertions. The built in assertions in JUnit 5, whilst more advanced than JUnit 4 are still very far behind what can be done in AssertJ. The original "assertThat" library Hamcrest has not been updated since 2019. Google Truth is very similar to AssertJ but has a smaller developer pool and a slower rate of enhancement. 

### [Mockito](https://site.mockito.org/) to mock dependencies and inspect usage

Mockito is the leader in mocking frameworks, allowing objects and interfaces to be quickly simulated in test code, ensuring your unit test doesn't break due to failurs in code not under test. Since v3.4.0 it now supports mocking static classes, removing the main reason people needed to keep using the abandonded PowerMock project.

### [Lombok](https://projectlombok.org/) library to reduce the amount of code you write (and thus need to test)

Lombok is a very popular library which provides low-level support for writing less code in your projects. Some of the features we use in this workshop are the getter/setter generation and the builder generation. This reduces the amount of code you need to write both in your POJOs and in the services that use them. This, of course, reduces the amount of tests you need to write.

## Pre-requisites

You need to have installed...

- JDK v8 or above : **java -version** must work at your command prompt.
- Apache Maven and some familiarity with it : **mvn -version** must work at your command prompt.
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

Before starting these modules, please be sure you have...

- Met the pre-requisites described above
- Cloned the code to your workstation
- Imported the maven project into your IDE
- are able to build the project at the command line
- are able to run the Main class in your IDE

There are no fixed "correct" answers in this workshop. The "solution" represents just one suggested way of implement unit tests.

## Exercise 1 : Running tests and viewing coverage

We will start with running some simple tests to get the feel of it all. Find the **StopTest** class. You will see that one test is already written for you. Have a look at the test. At this point, note how we have a **TestUtils** class in place that provides some static functions. You are free to add further functions to this class as this workshop progresses to help reuse code. Let's run it by using the following techniques:

- Run the test using your IDE. In JetBrains IDEA, a green play button appears by the test method name which you can click to run. Observe the results and check execution is successful.
- Debug the test using your IDE. You can put a breakpoint in the test method or the code under test. Observe the execution stops at your breakpoint. Inspect the values. Continue execution and observe the results.
- Run all the tests in **StopTest** in your IDE. A green play button is available by the class name in IDEA.
- Run all the tests in the project in your IDE.

Now do the same again, but his time **run with coverage** in your IDE. This will give you a report showing how much of the codebase has been tested. You can see the number is very small just now! As you progress through this module, you will write more and more tests, improving this coverage number. You can dive into the report in your IDE and double-click to open the class. Note how the coverage is indicated by red/yellow/green. These indicate no coverage/partial coverage (not all branches)/fully covered respoectively.

You can also run the tests outside your IDE, at the command line. This project is also configured (via the jacoco maven plugin in the pom.xml) to produce a coverage report when the tests are run.

- Run all the tests in the entire project at the command line by using ```mvn test```. Observe the results.
- Open the generated coverage report which can be found in **target\site\jacoco\index.html** and take a look.

Throughout this workshop, we will use [AssertJ](https://assertj.github.io/doc/) to make assertions instead of the rather primitive assertion features built into JUnit 5.

## Exercise 2 : Simple Unit Testing

Now that you can execute tests and view coverage, let's proceed and write some simple tests on POJOs within the system. Doing this will build your knowledge of the system and get you writing tests in the arrange/act/assert way. Since we are using Lombok, coverage will be good even if we do not test getters/setters. Remember to make assertions too, using [AssertJ](https://assertj.github.io/doc/)!

- Implement all test method stubs in StopTest
- Implement all test method stubs in PassengerTest as indicated for Module 2
- Implement all test method stubs in ServiceTest as indicated for Module 2

The stubs are a guideline, so please feel free to change as you see fit. When done try running your tests using the techniques you learned in exercise 1. How much has your coverage gone up by? Are there any parts of the classes under test that are not yet tested? If yes, don't worry - we will look at this in the next exercise!

## Exercise 3 : Testing Exceptions

If you run your test coverage report after doing exercise 2, you will notice that the uncovered code is the part that throws exceptions. JUnit 5 provides a nice way of testing these and making assertions on them. Read chaper 6 of [A Guide to JUnit 5](https://www.baeldung.com/junit-5) and then go on to implement the remaining method stubs in **StopTest**, **PassengerTest** and **ServiceTest**.

Once done, run your coverage report again and it should be looking pretty good for the three model classes we have tested so far! 

You may have noticed that some of the classes we have tested so far extend a base class **Identified**. However, if you look at that class there is no behaviour to test, so there is nothing special to do here - subclasses can be tested like any other. In the next exercise we will encounter a base class that does indeed have behaviour to test and we will see how to test it.

There are also some model classes we have not yet tested. We will look at these when we cover some advanced testing scenarios.

## Exercise 4 : Testing Services and Base class behaviour

We now move onto looking at testing a service, the **AccountServiceImpl**. This is the simplest of the services and can be tested in a similar way to the POJOs you have already tested. The main thing that is new here is the base class **BaseService** contains two methods - one private and one public. Best practice outlined in [Unit Test Private Methods in Java](https://www.baeldung.com/java-unit-test-private-methods) means that we only need to test the public method. If the private method is not fully tested as we approach 100% test coverage, then it has dead code that should be removed. Bear this in mind when writing tests in the real world.

When testing services it can be useful to use the JUnit 5 test lifecycle **@BeforeEach** annotation to ensure that you have a fresh service before each test runs. This guarantees that your tests are stateless and reduces code duplication. The test class skeleton is prepared in this way.

Now go ahead and implement the test stubs in **AccountServiceImplTest**. 

If you want to dive into alternative methods for testing abstract base classes, you can look at [Testing an Abstract Class With JUnit](https://www.baeldung.com/junit-test-abstract-class).

## Exercise 5 : Mocking External Behaviour

In the previous exercise, the service we tested did not have any external dependencies. It did not use any other services to achieve its desired operation. In this exercise we now look at another service in this system, the **BookingServiceImpl** which you can see makes use of the **NetworkService**, specifically to calculate a route between two stations so a price can be determined.

However, when testing the BookingServiceImpl we only want to test that "unit" of code, not the NetworkService. In other words, we are trying to determine if the BookingServiceImpl works as expected not the NetworkService it uses. Thus, if the NetworkService breaks in some way, this should not fail the BookingServiceImpl tests. This is a core reason why mocking external dependencies is required at this point. Now we could simply implement a cut down test version of the NetworkService and wire that up when testing, but there is a popular library that can help: Mockito.  Mockito is already added to this project, so no need to add it to the pom.xml.

You should now review the **BookingServiceImplTest** class and implement the remaining methods. Use Mockito **when** and **verify** features to mock calls to the external NetworkService dependency and verify that the calls were made by the Booking service under test.

## Exercise 6 : Partial Mocks

When testing certain methods in business service classes you may find that they use one or more other methods to achieve a high level operation. If these other methods are in different classes, you now know how to use Mockito to mock them and make asserions on the behaviour. However, what if these other methods are in the same class?

Mockito can also help with this. We will now look at [Using Spies](https://www.baeldung.com/mockito-spy). A Mockito "Spy" allows us to use a real object and mock some of the methods in side it when we need to.

Now open the **TimetableServiceImplTest** class. Implement the methods indicated for this exercise. Remember, we are building more skills here and testing a real application, so you may need to use some of the previous techniques you have learned so far in this workshop to achieve good test coverage. If you notice that a method you are testing calls another method in the class, you can mock it by using the Mockito **doReturn().when()...** syntax instead of the **when().thenReturn()...** we have used previously for fully mocked objects.

Using partial mocks can be very helpful with complex classes to isolate functionality in methods to ensure you testing remains focussed on the unit under test. This can help you build more resilient tests that are less prone to breaking when functionality they are dependent on has a problem.

## Exercise 7 : Mocking Static Methods

So far we have looked at how to mock instances of classes, but what happens if you need to mock a static method? In many cases, mocking a static method does not make sense. Static methods often come from third part libraries, such as the **Preconditions.checkArgument()** you see in this project. They can also be found in the JDK, for example **BigDecimal.valueOf()** also used here. It can _sometimes_ make sense to mock static methods when you own the implementation, it is complex, and you want to isolate the testing of it to it's own test class.

In our project we have a **RailwayUtils** class which provides static methods to the rest of the system, including a **parseFile()** function that does some moderately complex parsing. Staying with **TimetableServiceImpl** we can see that the **loadServices()** method uses it. So here we have a situation where we may benefit from mocking the static function.

Let's go ahead and implement the **shouldLoadServicesFromFileFullyMocked()** in the **TimetableServiceImplTest** class to test the **loadServices()** method. You can static mock the **RailwayUtils.parseFile()** function and also use your new partial mocking skills to mock other methods used in the same class, so isolate testing to just the functionality expressed in the method under test. 

## Exercise 8 : Parameterized Tests - built in sources

We will now take a look at JUnit's parameterized tests. You can read the [Guide to JUnit 5 Parameterized Tests](https://www.baeldung.com/parameterized-tests-junit-5) to familiarize yourself with the feature, which allows you to run a test multiple times with different data.

In this exercise we are going to look at a few different types of parameterized test and give you the opportunity to write some tests using the built-in data source providers...

1. Implement **RailwayUtilsTest.shouldParseDouble()** using a **@ValueSource**.
1. Implement **RailwayUtilsTest.shouldParseDoubleAsExpected()** using a **@CsvSource** to provide both the input and expected output.
1. Implement all test methods in **ConcessionTest** using a **@EnumSource** to enforce certain restrictions on the concessions.

When you run your parameterized tests, note how the output is reported. You will see that each data point you provide is considered as a separate test.

## Exercise 9 : Parameterized Tests - method source

The built-in sources are useful, but are less suited to use with complex objects. Indeed, you will have noticed that you needed to convert a result to string when writing your @CsvSource test in the previous exercise. Fortunately, JUnit 5 provides a way of providing complex objects to your parameterized tests in the form of method source.

This fantasy railway system contains a **GraphUtils** class which offers a **findShortestPath()** generic function. This is a good candidate for a method source parameterized test. We can define a custom graph, ask for shortest paths and provide the expected list by way of method source.

Using this technique, you can now implement the test **GraphUtils.shouldFindTheShortestPath()**. A function is already provided which will give you a sample graph. You can use it to help arrange your test data prior to executing the test. Your method source is not provided - you will need to write it yourself.

## Exercise 10 : Dynamic Tests

Let's take a quick look at JUnit 5 dynamic tests. You can learn about this feature in the [Guide to Dynamic Tests in Junit 5](https://www.baeldung.com/junit5-dynamic-tests). In a nutshell, dynamic tests are created by your code and are only really limited by your imagination! In practice, there are few compelling reasons to use a dynamic test. Try searching your company repo for usages and see what you find. They can find use in permutation and combination testing such as that found within the [Chronicle Test Framework](https://github.com/OpenHFT/Chronicle-Test-Framework).

In the **GraphUtilsTest** class, implement the **shouldAlwaysCalculateRoute()** so that it asserts a route is found between two randomly chosen nodes at least ten times. You will need to change the return type and add the required annotation.

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
