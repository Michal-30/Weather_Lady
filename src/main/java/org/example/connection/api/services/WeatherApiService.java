package org.example.connection.api.services;

import org.example.connection.api.WeatherApiHttp;
import org.example.connection.api.WeatherUtils;
import org.example.connection.db.daos.GenericDao;
import org.example.connection.db.models.Weather;
import org.example.connection.db.services.GenericService;

public class WeatherApiService {
    private final double lat;
    private final double lon;
    private final WeatherApiHttp weatherApiHttp;
    private final GenericDao<Weather, Long> weatherDao;
    private final GenericService<Weather, Long> weatherService;

    public WeatherApiService(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.weatherApiHttp = new WeatherApiHttp();
        this.weatherDao = new GenericDao<>(Weather.class);
        this.weatherService = new GenericService<>(weatherDao);
    }

    public void saveWeathers() {
        String openMeteoResponse = weatherApiHttp.getOpenMeteoData(this.lat, this.lon);
        String openWeatherResponse = weatherApiHttp.getOpenWeatherData(this.lat, this.lon);

        Weather weather = new WeatherUtils().createWeather(openMeteoResponse, openWeatherResponse);

        weatherService.save(weather);
    }


    public static void main(String[] args) {
        WeatherApiService weatherApiService = new WeatherApiService(52.52, 13.41);
    }
}
