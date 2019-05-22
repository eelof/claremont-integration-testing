package se.claremont.tutorial.weather.app;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WeatherRestController {

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello!";
    }
}