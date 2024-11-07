package org.example.connection.services;

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
    private final GenericService<Location, Long> locationService = new GenericService<>(locationDao);
    private List<Location> cityLocationList = new ArrayList<>();

    public LocationService() {
    }

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
                this.cityLocationList.add(l);
            }
        });
    }

    //
    private List<Location> coordinateLocationList(GeocodeService geocodeService){
        List<Location> coordinateLocationList = new ArrayList<>();
        geocodeService.getCoordinates().forEach(c-> {
            GeocodeService geocodeCoordinatesLocations = new GeocodeService(c.getLatitude(), c.getLongitude());
            coordinateLocationList.add(location(geocodeCoordinatesLocations));
        });return coordinateLocationList;
    }

    //create locationObject with details
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

    public void saveToDB(Location location){
        this.locationService.save(location);
    }

    public void saveAllCityLocationsToDB(){
        this.cityLocationList.forEach(this.locationService::save);
    }

    public void deleteFromDB(Location location){
        this.locationService.delete(location);
    }

    public void deleteAllCityLocationsFromDB(){
        this.cityLocationList.forEach(this.locationService::delete);
    }

    public void updateToDB(Location location){
        this.locationService.update(location);
    }

    public void updateAllCityLocationsFromDB(){
        this.cityLocationList.forEach(this.locationService::update);
    }

    public Location getCityByIdFromDB(long id){
        return this.locationService.getById(id);
    }

    public List<Location> getAllLocationsFromDB(){
        List<Location> returnList = new ArrayList<>();
        long id = 1;
        boolean isNotEmpty = true;
        while(isNotEmpty){
            if(this.locationService.getById(id) != null){
                returnList.add(this.locationService.getById(id));
                id = id + 1;
            }else{isNotEmpty = false;}
        }return returnList;
    }

    public List<Location> getAllCityLocationsFromDB(String cityName){
        return getAllLocationsFromDB().stream().filter(l->l.getCity().equalsIgnoreCase(cityName)).toList();
    }

}
