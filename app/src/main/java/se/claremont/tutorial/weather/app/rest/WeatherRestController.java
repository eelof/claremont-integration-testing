package se.claremont.tutorial.weather.app.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import se.claremont.tutorial.weather.app.service.WeatherService;

@RestController
public class WeatherRestController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/{country}/{city}")
    public ResponseEntity weather(@PathVariable String country, @PathVariable String city) {
        return weatherService.getWeather(country, city);
    }
}