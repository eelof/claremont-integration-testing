package se.claremont.tutorial.weather.it;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;

import java.io.IOException;
import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

public class WeatherIT {

    MockServerClient mockServerClient = new MockServerClient("localhost", 1080);

    @After
    public void after() {
        mockServerClient.reset();
    }

    @Test
    public void getWeather_validRequest_shouldReturnWeatherStateAndAdjective() {
        mockServerClient.when(
            request()
                .withMethod("GET")
                .withPath("/data/2.5/weather"))
            .respond(
                    response()
                    .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                    .withBody(getResourceContents("openweather-responses/weather_stockholm_clear_19.json"))
            );

        get("/weather/SE/Stockholm")
            .prettyPeek()
            .then()
            .assertThat().content(containsString("soligt"))
            .and().content(containsString("Härligt!"));
    }

    @Test
    public void getWeather_validRequest_shouldreturnStatus200() {
        mockServerClient.when(
            request()
                    .withMethod("GET")
                    .withPath("/data/2.5/weather"))
            .respond(
                    response()
                    .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                    .withBody(getResourceContents("openweather-responses/weather_stockholm_clear_19.json"))
            );

        get("/weather/SE/Stockholm")
            .prettyPeek()
            .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void getWeather_invalidCountry_shouldReturnStatus400() {
        get("/weather/XX/Stockholm")
                .prettyPeek()
                .then()
                .assertThat().statusCode(400);
    }

    @Test
    public void getWeather_cityNotFound_shouldReturnStatus404() {
        get("/weather/SE/SomeCityThatDoesn'tExist")
                .prettyPeek()
                .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void getWeather_invalidUrl_shouldReturnStatus404() {
        get("/weathar/SE/Stockholm")
                .prettyPeek()
                .then()
                .assertThat().statusCode(404);
    }

    private String getResourceContents(String path) {
        try {
            return Resources.toString(Resources.getResource(path), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
