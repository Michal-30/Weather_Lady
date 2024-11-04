package org.example.connection.api;

import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.example.connection.db.daos.GenericDao;
import org.example.connection.db.models.Location;
import org.example.connection.db.services.GenericService;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GeocodeService {
    private final String apiKey = "672603415fb3e308262303soqf25b0d";
    private final List<Location> locations = new ArrayList<>();
    private final String search;
    private final String urlSearch;
    private final GenericDao<Location, Long> locationDao = new GenericDao<>(Location.class);
    private final GenericService<Location, Long> locatonService = new GenericService<>(locationDao);

    public GeocodeService(String search) {
        this.search = search;
        urlSearch = String.format("https://geocode.maps.co/search?q=%s&api_key=%s",search, this.apiKey);
        coordinates();
        locatios();
    }

    public List<Location> getLocations() {
        return locations;
    }

    public String jsonString(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            // Optional: set headers (if the API requires authentication or specific headers)
            request.addHeader("Accept", "application/json");
            request.addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN");

            // Execute the request
            HttpResponse response = httpClient.execute(request);

            // Check for successful response code
            if (response.getStatusLine().getStatusCode() == 200) {
                Thread.sleep(1000);
                // Parse response
                return EntityUtils.toString(response.getEntity());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Coordinates> coordinates() {
        List<Coordinates> coordinates = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString(this.urlSearch));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            double latitude = item.getDouble("lat");
            double longitude = item.getDouble("lon");
            coordinates.add(new Coordinates(latitude, longitude));
        }return coordinates;
    }

    public void locatios(){
        coordinates().forEach(c->{
            String url = String.format("https://geocode.maps.co/reverse?lat=%s&lon=%s&api_key=%s",c.getLatitude(),c.getLongitude(),this.apiKey);
            if(locationByCoordinates(url).getCity() != null && locationByCoordinates(url).getCity().equalsIgnoreCase(this.search)) {
                this.locations.add(locationByCoordinates(url));
            }
        });
    }

    static void scannerJsonObject(JSONObject jsonObject, Location location){
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if(key.equals("county")){location.setCounty(jsonObject.get(key).toString());};
            if(key.equals("municipality")){location.setMunicipality(jsonObject.get(key).toString());};
            if(key.equals("suburb")){location.setSuburb(jsonObject.get(key).toString());};
            if(key.equals("country")){location.setCountry(jsonObject.get(key).toString());};
            if(key.equals("city")){location.setCity(jsonObject.get(key).toString());};
        }
    }

    public Location locationByCoordinates(String url) {
        Location newLocation = new Location();
        JSONObject locationObject = new JSONObject(jsonString(url));
        newLocation.setLatitude(locationObject.getDouble("lat"));
        newLocation.setLongitude(locationObject.getDouble("lon"));
        JSONObject addressObject = locationObject.getJSONObject("address");
        scannerJsonObject(addressObject,newLocation);
        return newLocation;
    }

    public void saveLocations() {
        this.locations.forEach(l-> {
            locatonService.save(l);
        });
    }

    public static void main(String[] args) {
        GeocodeService locations = new GeocodeService("brno");
        locations.saveLocations();
        locations.getLocations().forEach(l-> {
            WeatherApiService weatherApiService = new WeatherApiService(l.getLatitude(),l.getLongitude());
            weatherApiService.saveWeathers();
        });

    }
}