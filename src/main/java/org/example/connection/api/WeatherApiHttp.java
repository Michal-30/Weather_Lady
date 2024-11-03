package org.example.connection.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherApiHttp {
    private final HttpClient httpClient;
    private final String openMeteoUrl;
    private final String openWeatherUrl;
    private final String openWeatherApiKey;

    public WeatherApiHttp() {
        // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,relative_humidity_2m,cloud_cover,wind_speed_10m&timezone=Europe%2FBerlin
        // https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&units=metric&appid=ec7b93991f85cb151e6257361ef3543e
        this.openMeteoUrl = "https://api.open-meteo.com/v1/forecast";
        this.openWeatherUrl = "https://api.openweathermap.org/data/2.5/weather";
        this.openWeatherApiKey = "ec7b93991f85cb151e6257361ef3543e";
        this.httpClient = HttpClient.newHttpClient();
    }

    public String getOpenMeteoData(double lat, double lon) {
        String url = this.openMeteoUrl + "?latitude=" +
                lat +
                "&longitude=" +
                lon +
                "&current=temperature_2m,relative_humidity_2m,cloud_cover,wind_speed_10m&timezone=Europe%2FBerlin";

        return this.httpRequest(url);
    }

    public String getOpenWeatherData(double lat, double lon) {
        String url = this.openWeatherUrl + "?lat=" +
                lat +
                "&lon=" +
                lon +
                "&units=metric&appid=" +
                openWeatherApiKey;

        return this.httpRequest(url);
    }

    private String httpRequest(String url) {
        try {
            URI uri = new URI(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
