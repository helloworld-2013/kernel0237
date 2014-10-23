Welcome to Email Domain Blacklister
-----------------------------------

The program is implemented as simple 3 tiers application : UI , Business , Persistence. I use Bootstrap 3.2 for UI,
 and Spring MVC with Jackson Mapper for controller. I use Spring tx for Business create and delete methods. I use Spring orm
 and Hibernate for Persistence with JPA support.

Tomcat7 plugin was chosen because Tomcat is the most widely used open source web server and I want the program to be able to live in it.

The program needs below environment settings:
* Properly configured Apache Maven 3.x.x and its executable mvn must be in PATH variable.

Steps to enjoy Email Domain Blacklister:
* Execute run.bat (Windows) / run.sh (MacOS or Linux)
* After the server is up at 8080 , browse to http://localhost:8080/EmailDomainBlacklister
* I have preloaded the H2 database with some data , to connect to the H2 instance: java -cp <h2.jar file> org.h2.tools.Shell -url jdbc:h2:tcp://localhost:8082/path/to/exploded/workspace/blacklist;ifexists=true -user sa

Some assumptions during the release of Email Domain Blacklister:
* User wouldn't mind with UI grid without pagination
* User wouldn't mind with client side searching
* The amount of blacklisted domains would not be huge