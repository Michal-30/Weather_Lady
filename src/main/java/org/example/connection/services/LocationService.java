package org.example.connection.services;

import org.example.connection.api.geocode.Coordinates;
import org.example.connection.api.geocode.GeocodeService;
import org.example.connection.db.daos.GenericDao;
import org.example.connection.db.models.Location;
import org.example.connection.db.services.GenericService;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocationService {
    private final GenericDao<Location, Long> locationDao = new GenericDao<>(Location.class);
    private final GenericService<Location, Long> locatonService = new GenericService<>(locationDao);
    private List<Location> cityLocationList = new ArrayList<>();

    public LocationService(String cityName) {
        GeocodeService geocodeSearchService = new GeocodeService(cityName);
        filterCoordinatesToCityLocations(geocodeSearchService);
    }

    public List<Location> getCityLocationList() {
        return cityLocationList;
    }

    private void filterCoordinatesToCityLocations(GeocodeService geocodeService){
        coordinateLocationList(geocodeService).forEach(l-> {
            if(l.getCity() !=null){
                System.out.println(l);
                this.cityLocationList.add(l);
            }
        });
    }


    private List<Location> coordinateLocationList(GeocodeService geocodeService){
        List<Location> coordinateLocationList = new ArrayList<>();
        geocodeService.getCoordinates().forEach(c-> {
            GeocodeService geocodeCoordinatesLocations = new GeocodeService(c.getLatitude(), c.getLongitude());
            coordinateLocationList.add(location(geocodeCoordinatesLocations));
        });return coordinateLocationList;
    }

    //it is work
    private Location location(GeocodeService geocodeCoordinatesLocations){
        Location newlocation = new Location();
        newlocation.setLatitude(geocodeCoordinatesLocations.getCoordinates().getFirst().getLatitude());
        newlocation.setLongitude(geocodeCoordinatesLocations.getCoordinates().getFirst().getLongitude());
        JSONObject adressJsonObject = geocodeCoordinatesLocations.getAddressObject();
        Iterator<String> keys = adressJsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if(key.equals("county")){newlocation.setCounty(adressJsonObject.get(key).toString());};
            if(key.equals("municipality")){newlocation.setMunicipality(adressJsonObject.get(key).toString());};
            if(key.equals("suburb")){newlocation.setSuburb(adressJsonObject.get(key).toString());};
            if(key.equals("country")){newlocation.setCountry(adressJsonObject.get(key).toString());};
            if(key.equals("city")){newlocation.setCity(adressJsonObject.get(key).toString());};
        }return newlocation;
    }
}
