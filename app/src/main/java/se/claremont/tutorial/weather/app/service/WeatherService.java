package se.claremont.tutorial.weather.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import se.claremont.tutorial.weather.app.error.InvalidCountryException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class WeatherService {

    RestTemplate restTemplate;
    ObjectMapper objectMapper;
    Map<String, String> countries;

    @Autowired
    public WeatherService(@Value("${api.url:http://api.openweathermap.org}") String apiUrl) {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(apiUrl));
        objectMapper = new ObjectMapper();

        countries = new HashMap<String, String>();
        for(Locale loc : Locale.getAvailableLocales()) {
            countries.put(loc.getDisplayCountry(), loc.getCountry());
        }
    }

    public ResponseEntity getWeather(String country, String city) {
        if(!countries.containsKey(country) && !countries.containsValue(country)) throw new InvalidCountryException(country);

        String url = "/data/2.5/weather?q="+city+","+country+"&appid=8363b5cad8db680617e9f9f57434a47f";

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

        System.out.println(url);
        try {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(transformWeatherJsonToPrettyResponse(response.getBody()));
    }

    public String transformWeatherJsonToPrettyResponse(JsonNode json) {
        //JsonNode json = weatherStringToJson(jsonString);
        double temp = kelvinToCelsius(json.path("main").path("temp").doubleValue());
        String weather = json.path("weather").get(0).path("main").asText();
        String city = json.get("name").asText();


        return "Det är "
                + weatherToAdjective(weather) + temp + " grader varmt i " + city + ". " + tempToReaction(temp);
    }

    private String weatherToAdjective(String weatherDescription) {
        switch (weatherDescription) {
            case "Rain": return "regnigt och ";
            case "Clouds": return "molnigt och ";
            case "Drizzle": return "duggigt och ";
            case "Clear": return "soligt och ";
            default: return "";
        }
    }

    private String tempToReaction(double temp) {
        return temp > 15 ? "Härligt!" : "Kyligt!";
    }

    private double kelvinToCelsius(double kelvin) {
        return BigDecimal.valueOf(kelvin-273.15).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
