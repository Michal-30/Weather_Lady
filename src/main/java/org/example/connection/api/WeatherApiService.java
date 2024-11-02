package org.example.connection.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherApiService {
    private HttpClient httpClient;
    private String apiUrl;

    public WeatherApiService() {
        this.apiUrl = "https://api.open-meteo.com/v1/forecast";
        this.httpClient = HttpClient.newHttpClient();
    }

    public String getWeatherByCord(double lat, double lan) {
        StringBuilder url = new StringBuilder(this.apiUrl).append("?latitude=")
                .append(lat)
                .append("&longitude=")
                .append(lan)
                .append("&current=temperature_2m&daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FBerlin");

        try {
            URI uri = new URI(url.toString());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            return "[{}]";
        }
    }
}
