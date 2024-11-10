package org.example.api.meteo;

import org.example.api.ApiConnect;
import org.example.api.geocode.Coordinates;

import java.net.http.HttpClient;

public class WeatherApiHttp {
    private ApiConnect apiConnect;
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

    public String getOpenMeteoData(Coordinates coordinates) {
        String url = this.openMeteoUrl + "?latitude=" +
                coordinates.getLatitude() +
                "&longitude=" +
                coordinates.getLongitude() +
                "&current=temperature_2m,relative_humidity_2m,cloud_cover,wind_speed_10m&timezone=Europe%2FBerlin";
        ApiConnect apiConnect = new ApiConnect(url);
        return apiConnect.getJsonString();
    }

    public String getOpenWeatherData(Coordinates coordinates) {
        String url = this.openWeatherUrl + "?lat=" +
                coordinates.getLatitude() +
                "&lon=" +
                coordinates.getLongitude() +
                "&units=metric&appid=" +
                openWeatherApiKey;
        ApiConnect apiConnect = new ApiConnect(url);
        return apiConnect.getJsonString();
    }
}
