# Build from sources

## Using the API:

### Download GIT

Before all you need to have Git or a GUI (like TortoiseGit) installed on your Computer.

To download Git : https://git-scm.com/downloads
Java version The current version of Java used in this API is the
jdk-11.0.2 : https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html
⚠️When downloading new jdk, don't forget to change the environment variables of your system.

### Download API

If you are using Git without any UI, open a Git Bash console and use the command: git
clone https://github.com/faridaCH/OfferTechnicalTest2.git

Opening the project Open the project with IntelliJ or any Java IDE.

⚠️ The project is using Maven for its dependency, it may take some time for Maven to download them.

### The API

Spring The API was made using the v2.6.1 of Spring Boot.

### Database

The API use an empty embedded database H2.

### Logged information

The current API logs the information of all call the API in the console and in 2 different files that are situated i:

logs/AOP/application.log to register the information about each request using the Aspect Oriented Programming .
logs/Error/application.log to register all Error . All the information about the logging are located in the folder:
src/main/resources/log4j2.xml and helpers/LoggingUserAspect

The default value for the logs level are debug for the AOP, Hibernate and Spring logger and error for the Error.

#### How to use the API:

Start the project To start the project, Run the UserApiApplication situated in the src/main/java/com/offertest/UserAPI
folder.

The API is currently using the basic configuration, meaning it will be usable on localhost with a specific port (8080
for Windows).

If you are using another operating system than Windows, the port will be displayed in the console of the IDE used like:
2022-11-13T21:58:09.819+0100 INFO Tomcat initialized with port(s): 8080 (http)

### Available Call

The current API allow three requests:

###### Get to http://localhost:8080/testOffert/v1/userApi/user/all to recuperate  all  Users present in the database.

###### Get to http://localhost:8080/testOffert/v1/userApi/user/{id} where {id} is the id of the user you want to recuperate .

If we haven't any user with the "Id", the API return an Error 404 User not found.

###### Get http://localhost:8080/testOffert/v1/userApi/user/search=keyWord  to recuperate all users  where  the username contains "keyword" .

Post to http://localhost:8080/testOffert/v1/userApi/user/save
The body of the Post request must contain the information of the User in JSON format. ⚠ For a user to be created, he
needs three variable, a username, a country and a birthdate. ⚠ The username have no restriction(username length > 3 in
the API). ⚠ The country must be France if you want the User to be registered successfully. ⚠ The birthdate must be in
yyyy-mm-dd to be valid, and The user must be an Adult (above 18 years old) to be registered successfully. ⚠ The user
also possesses two "optional" variables, a gender and a phone. Currently, the API only accept "Man"  "Woman"  as a
gender. The Phone number accept different format (xx xx xx xx xx, +33x xx xx xx xx, xx xxxx xxxx , xx-xx-xx-xx-xx,
xxxxxxxxxx ...)

#### Tests ( to complete )

All the tests are contained within the src/test/java/com/offertest/UserAPI folder.

To run the test and see the result, right-click on the test and select Run.

###### Unit tests

The UserServiceTest use a Mock repository to test if our Service work properly. The UserApiTest use a Mock Service and a
Mock Mvc test if our API handle the request properly.

###### Integration tests

The integration test is made in 2 steps:

###### # The test between the UserApi and the UserService with a Mock Mvc to emulate the Post/Get message.

###### # The test between Postman and our API.

For this test, the API must be running and Postman used to send Post/Get message. A collection of Postman request is
present within the Tests Postman folder. Within this collection, the requests are separated in three:

#### Swagger Documentation

the swagger documentation is contained in the url :
Swagger documentation : http://localhost:8080/swagger-ui.html
 Swagger OpenApi documentation : http://localhost:8080/v3/api-docs
