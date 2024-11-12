package org.example.api;

import lombok.Getter;
import org.example.db.models.Location;
import org.example.db.services.GenericService;
import org.example.models.GeoCodeResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocationApiService {
    private final List<Location> cityLocationList = new ArrayList<>();
    @Getter private static final ApiClient client = new ApiClient();

    public LocationApiService(GenericService<Location, Long> locationService) {
    }

    public List<Location> search(String cityName) {
        cityLocationList.clear();

        var geoResponse = client.fetchGeoCodeResponses(cityName);
        filterCoordinatesToCityLocations(geoResponse);
        return this.cityLocationList;
    }


    private void filterCoordinatesToCityLocations(List<GeoCodeResponse> geoResponse){
        coordinateLocationList(geoResponse).forEach(l-> {
            if(l.getCity() != null){
                this.cityLocationList.add(l);
            }
        });
    }

    private Collection<Location> coordinateLocationList(List<GeoCodeResponse> geoResponses){
        List<Location> coordinateLocationList = new ArrayList<>();

        if (!geoResponses.isEmpty()) {
            geoResponses.forEach(g -> coordinateLocationList.add(location(g)));
        }
        return coordinateLocationList;
    }

    private Location location(GeoCodeResponse response){
        Location newlocation = new Location();
        GeoCodeResponse.Coordinates cords = response.getCoordinates();
        GeoCodeResponse.Address address = response.getAddress();

        newlocation.setCoordinates(cords);
        newlocation.setAddress(address);

        return newlocation;
    }
}
