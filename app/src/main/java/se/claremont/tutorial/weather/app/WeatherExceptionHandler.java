package se.claremont.tutorial.weather.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import se.claremont.tutorial.weather.app.error.InvalidCountryException;

@RestControllerAdvice
public class WeatherExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity handleHttpClientErrorException(HttpClientErrorException e) {

        switch(e.getStatusCode()) {
            case NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kan inte hitta staden du letar efter!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nåt gick fel :(");
    }

    @ExceptionHandler({InvalidCountryException.class})
    public ResponseEntity handleInvalidCOuntryException(InvalidCountryException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCountry() + " är inte ett giltigt landsnamn!");
    }
}
