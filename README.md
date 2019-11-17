# cucumber-restassured-helloworld

This is cucumber-restassured testing project for API testing.
Used checking responsebody using json scheme V4 (https://json-schema.org/)

Feature files are in /src/test/resources/service/

Steps are defined in: /src/test/java/service/Stepdefs.java

before run 
add Environment.csv to /src/test/resources/ 
with next data: 


baseUri - Base URI of the service.

wa_key - key for authentification on the service

Makers - 1-st endpoint

Models - 2-nd endpoint

Build Date - 3-rd endpoint
_____________________________________________________________
to run tests please execute in the command promt:" ./gradlew test "
