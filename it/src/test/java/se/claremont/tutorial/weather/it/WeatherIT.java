package se.claremont.tutorial.weather.it;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class WeatherIT {


    MockServerClient mockServerClient = new MockServerClient("localhost", 1080);

    @Test
    public void WeatherIT() throws IOException {
        mockServerClient.when(
                request()
                    .withMethod("GET")
                    .withPath("/data/2.5/weather"))
                .respond(
                        response()
                        .withBody(getResourceContents("openweather-responses/weather_stockholm.json"))
                );

        given()
                .get("/weather/Stockholm")
                .prettyPrint();
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
