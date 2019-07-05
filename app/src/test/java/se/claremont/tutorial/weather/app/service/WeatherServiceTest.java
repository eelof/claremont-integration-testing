package se.claremont.tutorial.weather.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;

class WeatherServiceTest {

    private WeatherService weatherService = new WeatherService("");

    @Test
    public void testTransformWeatherJsonToPrettyResponse() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readValue("{\"coord\":{\"lon\":18.07,\"lat\":59.33},\"weather\":[{\"id\":800,\"main\":\"Ukn\"," +
                "\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":293.3," +
                "\"pressure\":1010,\"humidity\":63,\"temp_min\":291.15,\"temp_max\":295.37},\"visibility\":10000," +
                "\"wind\":{\"speed\":2.6,\"deg\":60},\"clouds\":{\"all\":0},\"dt\":1558512908,\"sys\":{\"type\":1," +
                "\"id\":1788,\"message\":0.005,\"country\":\"SE\",\"sunrise\":1558490455,\"sunset\":1558553285}," +
                "\"id\":2673730,\"name\":\"Stockholm\",\"cod\":200}", JsonNode.class);

        System.out.println(
                weatherService.transformWeatherJsonToPrettyResponse(json)
        );
    }

    @Test
    public void countryCodeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Locale.getISOCountries())
        );

        for(Locale loc : Locale.getAvailableLocales()) {
            System.out.println(loc.getDisplayCountry());
            System.out.println(loc.getCountry());
        }
    }
}