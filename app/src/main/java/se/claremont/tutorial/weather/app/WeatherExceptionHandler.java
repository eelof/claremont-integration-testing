package se.claremont.tutorial.weather.app;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class WeatherExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class})
    public String handle404(HttpClientErrorException e) {

        switch(e.getStatusCode()) {
            case NOT_FOUND:
                return "Kan inte hitta det du letar efter!";
        }

        return "Nåt gick fel :(";
    }
}
