package org.example.db.models;


import lombok.Data;
import org.example.models.GeoCodeResponse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
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

    public void setAddress(GeoCodeResponse.Address address) {
        this.city = address.suburb();
        this.country = address.country();
        this.suburb = address.suburb();
        this.municipality = address.municipality();
        this.county = address.state();
    }

    public void setCoordinates(GeoCodeResponse.Coordinates coordinates) {
        this.latitude = coordinates.lat();
        this.longitude = coordinates.lon();
    }


    @Override
    public String toString() {

        return String.format("%s%s%s%s%s",
                this.suburb == null ? "" : this.suburb +", ",
                this.city == null ? "" : this.city +", ",
                this.municipality == null ? "" : this.municipality +", ",
                this.county == null ? "" : this.county+", ",
                this.country == null ? "" : this.country);
    }

}
