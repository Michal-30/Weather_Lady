package org.example.views.services;

import org.example.api.geocode.Coordinates;
import org.example.api.services.LocationApiService;
import org.example.api.services.WeatherApiService;
import org.example.db.daos.GenericDao;
import org.example.db.models.Location;
import org.example.db.models.Weather;
import org.example.db.services.GenericService;

import java.util.List;

public class ViewService {
    private final WeatherApiService weatherApiService;
    private final LocationApiService locationApiService;
    private final GenericDao<Location, Long> locationDao;
    private final GenericService<Location, Long> locationService;
    private final GenericDao<Weather, Long> weatherDao;
    private final GenericService<Weather, Long> weatherService;

    public ViewService() {
        this.locationDao = new GenericDao<>(Location.class);
        this.locationService = new GenericService<>(locationDao);
        this.locationApiService = new LocationApiService(locationService);
        this.weatherDao = new GenericDao<>(Weather.class);
        this.weatherService = new GenericService<>(weatherDao);
        this.weatherApiService = new WeatherApiService(weatherService);
    }

    public Location getCorrectLocation(Location location) {
        Location checkedLocation = this.locationService.getByCoords(new Coordinates(location.getLatitude(), location.getLongitude()));

        if (checkedLocation == null) {
            return this.locationService.save(location);
        }

        return checkedLocation;
    }

    public Weather createWeather(Location location) {
        this.weatherApiService.createWeather(location);

        return this.weatherApiService.getWeather();
    }

    public List<Location> search(String searchedLocation) {
        return this.locationApiService.search(searchedLocation);
    }

    public void save(Weather weather) {
        this.weatherService.save(weather);
    }

    public List<Weather> getDataForLocation(Location location) {
        return this.weatherService.getByLocation(location);
    }
}
