# claremont-integration-testing
This is an example application to illustrate integration testing using MockServer with Spring Boot. There will be an accompanying article published on claremont.se 

## Running the application

To run the application:
```
mvn clean install && java -jar app/target/app-1.0-SNAPSHOT.jar
```

To use the application, contact it on `localhost:8080/weather/<Country Code>/<City Name>`:

```shell
curl localhost:8080/weather/SE/Stockholm
> Det är molnigt och 16.56 grader varmt i Stockholm. Härligt!
```

## Running integration tests

1. Install and run MockServer: http://www.mock-server.com/mock_server/getting_started.html
2. Run the application according to instructions above
3. Open the project in your favorite IDE, such as IntelliJ, and navigate to `it/src/test/java/se/claremont/tutorial/weather/it/WeatherIT.java`
4. Run each test case using IntelliJ 
