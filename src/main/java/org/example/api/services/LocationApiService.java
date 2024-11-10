package org.example.api.services;

import org.example.api.geocode.GeocodeApi;
import org.example.db.models.Location;
import org.example.db.services.GenericService;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocationApiService {
    private final GenericService<Location, Long> locationService;
    private final List<Location> cityLocationList = new ArrayList<>();

    public LocationApiService(GenericService<Location, Long> locationService) {
        this.locationService = locationService;
    }

    public List<Location> search(String cityName) {
        cityLocationList.clear();

        GeocodeApi geocodeSearchService = new GeocodeApi(cityName);
        filterCoordinatesToCityLocations(geocodeSearchService);

        return this.cityLocationList;
    }


    private void filterCoordinatesToCityLocations(GeocodeApi geocodeService){
        coordinateLocationList(geocodeService).forEach(l-> {
            if(l.getCity() != null){
                this.cityLocationList.add(l);
            }
        });
    }

    private List<Location> coordinateLocationList(GeocodeApi geocodeService){
        List<Location> coordinateLocationList = new ArrayList<>();
        geocodeService.getCoordinates().forEach(c-> {
            GeocodeApi geocodeCoordinatesLocations = new GeocodeApi(c.getLatitude(), c.getLongitude());
            coordinateLocationList.add(location(geocodeCoordinatesLocations));
        });

        return coordinateLocationList;
    }

    private Location location(GeocodeApi geocodeCoordinatesLocations){
        Location newlocation = new Location();
        newlocation.setLatitude(geocodeCoordinatesLocations.getCoordinates().getFirst().getLatitude());
        newlocation.setLongitude(geocodeCoordinatesLocations.getCoordinates().getFirst().getLongitude());
        JSONObject adressJsonObject = geocodeCoordinatesLocations.getAddressObject();
        Iterator<String> keys = adressJsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if(key.equals("county")){newlocation.setCounty(adressJsonObject.get(key).toString());}
            if(key.equals("municipality")){newlocation.setMunicipality(adressJsonObject.get(key).toString());}
            if(key.equals("suburb")){newlocation.setSuburb(adressJsonObject.get(key).toString());}
            if(key.equals("country")){newlocation.setCountry(adressJsonObject.get(key).toString());}
            if(key.equals("city") || key.equals("town") || key.equals("village")){
                newlocation.setCity(adressJsonObject.get(key).toString());
            }
        }return newlocation;
    }
}
