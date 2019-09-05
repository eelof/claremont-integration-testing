package se.claremont.tutorial.weather.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import se.claremont.tutorial.weather.app.error.InvalidCountryException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherServiceTest {

    private WeatherService weatherService = new WeatherService("");
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void transformWeatherJsonToPrettyResponse_validJson_shouldTransformToPrettyResponse() throws IOException {
        JsonNode json = objectMapper.readValue("{\"coord\":{\"lon\":18.07,\"lat\":59.33},\"weather\":[{\"id\":800,\"main\":\"Ukn\"," +
                "\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":293.3," +
                "\"pressure\":1010,\"humidity\":63,\"temp_min\":291.15,\"temp_max\":295.37},\"visibility\":10000," +
                "\"wind\":{\"speed\":2.6,\"deg\":60},\"clouds\":{\"all\":0},\"dt\":1558512908,\"sys\":{\"type\":1," +
                "\"id\":1788,\"message\":0.005,\"country\":\"SE\",\"sunrise\":1558490455,\"sunset\":1558553285}," +
                "\"id\":2673730,\"name\":\"Stockholm\",\"cod\":200}", JsonNode.class);

        String result = weatherService.transformWeatherJsonToPrettyResponse(json);

        assertEquals("Det är 20.15 grader varmt i Stockholm. Härligt!", result);
    }

    @Test
    public void getWeather_invalidCountry_shouldThrowInvalidCountryException() {
        assertThrows(InvalidCountryException.class, () -> {
            weatherService.getWeather("XX", "Stockholm");
        });
    }
}