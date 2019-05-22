package se.claremont.tutorial.weather.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class WeatherService {

    RestTemplate restTemplate;


    @Autowired
    public WeatherService(@Value("${api.url:http://api.openweathermap.org}") String apiUrl) {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(apiUrl));
    }

    public String getWeather(String country, String city) {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/data/2.5/weather?q="+city+","+country+"&appid=8363b5cad8db680617e9f9f57434a47f", String.class);

        return transformWeatherJsonToPrettyResponse(response.getBody(), city);
    }

    public String transformWeatherJsonToPrettyResponse(String jsonString, String city) {
        JsonNode json = weatherStringToJson(jsonString);
        double temp = kelvinToCelsius(json.path("main").path("temp").doubleValue());

        return "Det är "
                + transformWeatherDescriptionToSwedishAdjective(json.path("weather").get(0).path("main").asText())
                + " " + temp + " grader varmt i " + city;
    }

    private String transformWeatherDescriptionToSwedishAdjective(String weatherDescription) {
        switch (weatherDescription) {
            case "Rain": return "regnigt och";
            case "Snow": return "snöigt och";
            case "Drizzle": return "duggigt och";
            case "Clear": return "soligt och";
            default: return "";
        }
    }

    private double kelvinToCelsius(double kelvin) {
        return BigDecimal.valueOf(kelvin-273.15).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private JsonNode weatherStringToJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

}
