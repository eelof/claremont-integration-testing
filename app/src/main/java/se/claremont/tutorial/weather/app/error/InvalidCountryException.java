package se.claremont.tutorial.weather.app.error;

public class InvalidCountryException extends RuntimeException {
    String country;

    public InvalidCountryException(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
