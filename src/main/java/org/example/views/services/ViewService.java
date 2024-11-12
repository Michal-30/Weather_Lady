package org.example.views.services;

import org.example.api.LocationApiService;
import org.example.db.daos.GenericDao;
import org.example.db.models.Location;
import org.example.db.models.Weather;
import org.example.db.services.GenericService;
import org.example.models.Coordinates;

import java.util.List;

public class ViewService {
    private final LocationApiService locationApiService;
    private final GenericService<Location, Long> locationService;
    private final GenericService<Weather, Long> weatherService;

    public ViewService() {
        GenericDao<Location, Long> locationDao = new GenericDao<>(Location.class);
        this.locationService = new GenericService<>(locationDao);
        this.locationApiService = new LocationApiService(locationService);
        GenericDao<Weather, Long> weatherDao = new GenericDao<>(Weather.class);
        this.weatherService = new GenericService<>(weatherDao);
    }

    public Location getCorrectLocation(Location location) {
        Location checkedLocation = this.locationService.getByCoords(new Coordinates(location.getLatitude(), location.getLongitude()));

        if (checkedLocation == null) {
            return this.locationService.save(location);
        }

        return checkedLocation;
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
