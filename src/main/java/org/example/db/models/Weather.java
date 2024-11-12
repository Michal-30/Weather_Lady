package org.example.db.models;

import lombok.Data;
import org.example.api.LocationApiService;
import org.example.models.GeoCodeResponse;
import org.example.models.OpenApiResponse;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("ALL")
@Data
@Entity
@Table(name = "weathers")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Location location;

    private LocalDateTime time;

    @Column(name = "temp") private double temp;
    @Column(name = "humidity") private int humidity;
    @Column(name = "cloud") private int cloud;
    @Column(name = "wind") private double wind;
    @Column(name = "pressure") private double pressure;


    public Weather() {}

    public Weather(final OpenApiResponse openApiResponse) {
        this.temp = openApiResponse.getTemperature();
        this.humidity = (int) openApiResponse.getHumidity();
        this.cloud = (int) openApiResponse.getClouds();
        this.wind = openApiResponse.getWindSpeed();
        this.time = openApiResponse.getDate();
        this.pressure = openApiResponse.getPressure();
    }

    public Weather(final Location loc) {
        var response = LocationApiService
                .getClient()
                .fetchOpenApiResponses(new GeoCodeResponse.Coordinates(loc.getLatitude(), loc.getLongitude()));




        this.temp = response.getTemperature();
        this.humidity = (int) response.getHumidity();
        this.cloud = (int) response.getClouds();
        this.wind = response.getWindSpeed();
        this.time = response.getDate();
        this.pressure = response.getPressure();
    }


    @Override
    public String toString() {
        return String.format("OpenApi    : (Temp:%3.2fc, Humidity:%3s%%, Cloud:%3s%%, Wind:%3.2fm/s)\n", this.temp, this.humidity, this.cloud, this.wind);
    }
}
