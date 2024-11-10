package org.example.api.services;

import org.example.api.geocode.Coordinates;
import org.example.api.meteo.WeatherApiHttp;
import org.example.api.meteo.WeatherUtils;
import org.example.db.models.Location;
import org.example.db.models.Weather;

public class WeatherApiService {
    private final WeatherApiHttp weatherApiHttp;
    private Weather weather;


    public WeatherApiService() {
        this.weatherApiHttp = new WeatherApiHttp();
    }

    public Weather getWeather() {
        return weather;
    }

    public void createWeather(Location location) {
        Coordinates openMeteoCoords = new Coordinates(location.getLatitude(), location.getLongitude());
        Coordinates openWeatherCoords = new Coordinates(location.getLatitude(), location.getLongitude());

        String openMeteoResponse = weatherApiHttp.getOpenMeteoData(openMeteoCoords);
        String openWeatherResponse = weatherApiHttp.getOpenWeatherData(openWeatherCoords);

        this.weather = new WeatherUtils().createWeather(openMeteoResponse, openWeatherResponse);
        this.weather.setLocation(location);
    }
}
