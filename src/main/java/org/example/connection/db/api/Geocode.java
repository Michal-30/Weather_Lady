package org.example.connection.db.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Geocode {
    private String url = "https://geocode.maps.co/reverse?";
    private double latitude;
    private double longitude;
    private String apiKey = "672603415fb3e308262303soqf25b0d";


    private String city;


    public Geocode() {
    }

    public Geocode(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void generateLocation() {
        String url = this.url+"lat="+this.latitude+"&lon="+this.longitude+"&api_key="+this.apiKey; // Replace with your API endpoint

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            // Optional: set headers (if the API requires authentication or specific headers)
            request.addHeader("Accept", "application/json");
            request.addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN");

            // Execute the request
            HttpResponse response = httpClient.execute(request);

            // Check for successful response code
            if (response.getStatusLine().getStatusCode() == 200) {
                // Parse response
                String jsonResponse = EntityUtils.toString(response.getEntity());
                System.out.println("Response: " + jsonResponse);
                Thread.sleep(1000);
                // Parse JSON and retrieve specific values
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                objects(jsonObject);

            } else {
                System.out.println("Error: " + response.getStatusLine().getStatusCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void objects(JsonObject jsonObject){
        this.city = jsonObject.getAsJsonObject("address").get("city").getAsString();
    }
}
