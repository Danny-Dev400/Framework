# Challenge Framework

## What type of framework did you choose?

I chose the type of framework called "data driven framework", this because I consider that for scalability it could be more comfortable for handling tests if the request inputs were given by external files that can be modified at any time.

## How are you handling the data?

Due to the type of framework chosen, the data is handled in external files. In this case, Json files were used to store variables, data and credentials useful for the execution of tests.

## What tools did you choose? And why?

### Builder project: Maven

Maven was used due to the ease of applying dependencies to the project and its ease of managing some environment variables and plugins. It is one of the most used and recommended builders on the market and in its repository "MVN repository" it has many dependencies to install.

### Test Runner: JUnit 4

Junit 4 was chosen for ease of use, it has a slightly easier learning curve and is a bit lighter when importing packages. This test runner was chosen instead of TestNg because many of the features of TestNg were not necessary and also they wanted to use a third-party reporting tool that was not integrated in the test runner and since Junit does not support reports natively, it was more comfortable implement allure.

## HTTP request tool: Rest Assured

rest assured was used as a tool to make requests and test the APIs. This tool was used for its ease and speed of implementation with different test runners, another important point is that using BDD syntax was necessary to better present the test cases proposed for the project.

### Report: allure

Allure was implemented because it is a reporting tool with a great scope, graphically it is one of the best in the market and it is perfect for presentations to clients and scalability over time. Its free and Open source.

