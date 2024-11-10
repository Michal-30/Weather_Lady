package org.example.db.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weathers")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Location location;

    private LocalDateTime time;
    @Column(name = "temp_meteo")
    private double tempMeteo;
    @Column(name = "humidity_meteo")
    private int humidityMeteo;
    @Column(name = "cloud_meteo")
    private int cloudMeteo;
    @Column(name = "wind_meteo")
    private double windMeteo;
    @Column(name = "temp_weather")
    private double tempWeather;
    @Column(name = "humidity_weather")
    private int humidityWeather;
    @Column(name = "cloud_weather")
    private int cloudWeather;
    @Column(name = "wind_weather")
    private double windWeather;

    public Weather() {
    }

    public Weather(double tempMeteo, int humidityMeteo, int cloudMeteo, double windMeteo, double tempWeather, int humidityWeather, int cloudWeather, double windWeather) {
        this.time = LocalDateTime.now();
        this.tempMeteo = tempMeteo;
        this.humidityMeteo = humidityMeteo;
        this.cloudMeteo = cloudMeteo;
        this.windMeteo = windMeteo;
        this.tempWeather = tempWeather;
        this.humidityWeather = humidityWeather;
        this.cloudWeather = cloudWeather;
        this.windWeather = windWeather;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public double getTempMeteo() {
        return tempMeteo;
    }

    public int getHumidityMeteo() {
        return humidityMeteo;
    }

    public int getCloudMeteo() {
        return cloudMeteo;
    }

    public double getWindMeteo() {
        return windMeteo;
    }

    public double getTempWeather() {
        return tempWeather;
    }

    public int getHumidityWeather() {
        return humidityWeather;
    }

    public int getCloudWeather() {
        return cloudWeather;
    }

    public double getWindWeather() {
        return windWeather;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", location=" + location +
                ", time=" + time +
                ", tempMeteo=" + tempMeteo +
                ", humidityMeteo=" + humidityMeteo +
                ", cloudMeteo=" + cloudMeteo +
                ", windMeteo=" + windMeteo +
                ", tempWeather=" + tempWeather +
                ", humidityWeather=" + humidityWeather +
                ", cloudWeather=" + cloudWeather +
                ", windWeather=" + windWeather +
                '}';
    }
}
