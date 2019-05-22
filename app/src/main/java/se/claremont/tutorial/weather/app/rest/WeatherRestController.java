package se.claremont.tutorial.weather.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import se.claremont.tutorial.weather.app.service.WeatherService;

@RestController
public class WeatherRestController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello!";
    }

    @GetMapping("/weather/{city}")
    public String weather(@PathVariable String city) {
        return weatherService.getWeather("SE", city);
    }
}