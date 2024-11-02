package org.example.connection.db.models;

public class Location {
    private final String id;
    private final double latitude;
    private final double longitude;
    private final String region;
    private final String country;

    public Location(String id, double latitude, double longitude, String region, String country) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.region = region;
        this.country = country;
    }

    // Gettery a settery
    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

}
