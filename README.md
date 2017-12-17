# enote
notebook project in Spring Lab

Create profiles: 'dev' for unit and 'integration-test' for integration tests.
'dev' is default profile.

Integration tests should start with IT*.java.

Unit tests should end with *Test.java.

To run integration tests:
mvn clean verify -P integration-test

To run web app:
mvn tomcat7:run-war
