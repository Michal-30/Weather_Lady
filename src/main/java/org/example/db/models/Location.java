package org.example.db.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;
    private String country;
    private String county;
    private String municipality;
    private String city;
    private String suburb;
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Weather> weathers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCounty() {
        return county;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    public List<Weather> getWeathers() {
//        return weathers;
//    }

    public Location() {
    }

//    public void setWeather(Weather weather){
//        weathers.add(weather);
//        //weather.setLocation(this);
//    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", county='" + county + '\'' +
                ", municipality='" + municipality + '\'' +
                ", city='" + city + '\'' +
                ", suburb='" + suburb + '\'' +
                '}';
    }
}
