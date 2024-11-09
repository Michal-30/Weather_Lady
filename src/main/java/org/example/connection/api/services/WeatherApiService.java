package org.example.connection.api.services;

import org.example.connection.api.openMeteo.WeatherApiHttp;
import org.example.connection.api.openMeteo.WeatherUtils;
import org.example.connection.db.daos.GenericDao;
import org.example.connection.db.models.Location;
import org.example.connection.db.models.Weather;
import org.example.connection.db.services.GenericService;

public class WeatherApiService {
    private Location location;

    private final WeatherApiHttp weatherApiHttp;
    private final GenericDao<Weather, Long> weatherDao;
    private final GenericService<Weather, Long> weatherService;

    public WeatherApiService(Location location) {
        this.location = location;
        this.weatherApiHttp = new WeatherApiHttp();
        this.weatherDao = new GenericDao<>(Weather.class);
        this.weatherService = new GenericService<>(weatherDao);
    }

    public void saveWeathers() {
        String openMeteoResponse = weatherApiHttp.getOpenMeteoData(this.location.getLatitude(), this.location.getLongitude());
        String openWeatherResponse = weatherApiHttp.getOpenWeatherData(this.location.getLatitude(), this.location.getLongitude());

        Weather weather = new WeatherUtils().createWeather(openMeteoResponse, openWeatherResponse);

        weather.setLocation(location);

        weatherService.save(weather);
    }


    public static void main(String[] args) {
        LocationService locationService = new LocationService("Volos");
        Location kavalaCity = locationService.getCityLocationList().getFirst();
        WeatherApiService weatherApiService = new WeatherApiService(kavalaCity);
        weatherApiService.saveWeathers();
    }
}
