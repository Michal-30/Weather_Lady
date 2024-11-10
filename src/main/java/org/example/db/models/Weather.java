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
    private Double tempMeteo;
    @Column(name = "humidity_meteo")
    private Integer humidityMeteo;
    @Column(name = "cloud_meteo")
    private Integer cloudMeteo;
    @Column(name = "wind_meteo")
    private Double windMeteo;
    @Column(name = "temp_weather")
    private Double tempWeather;
    @Column(name = "humidity_weather")
    private Integer humidityWeather;
    @Column(name = "cloud_weather")
    private Integer cloudWeather;
    @Column(name = "wind_weather")
    private Double windWeather;

    public Weather() {
    }

    public Weather(Double tempMeteo, Integer humidityMeteo, Integer cloudMeteo, Double windMeteo, Double tempWeather, Integer humidityWeather, Integer cloudWeather, Double windWeather) {
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

    private Double tempAverage(){
        if(this.tempMeteo == null){
            return this.tempWeather;
        }else if(this.tempWeather == null){
            return this.tempMeteo;
        }else {
            return (this.tempMeteo + this.tempWeather) / 2;
        }
    }

    private Integer humanityAverage(){
        if(this.humidityMeteo == null){
            return this.humidityWeather;
        }else if(this.humidityWeather == null){
            return this.humidityMeteo;
        }else {
            return (this.humidityMeteo + this.humidityWeather) / 2;
        }
    }

    private Integer cloudAverage(){
        if(this.cloudMeteo == null){
            return this.cloudWeather;
        }else if(this.cloudWeather == null){
            return this.cloudMeteo;
        }else {
            return (this.cloudMeteo + this.cloudWeather) / 2;
        }
    }

    private Double windAverage(){
        if(this.windMeteo == null){
            return this.windWeather;
        }else if(this.windWeather == null){
            return this.windMeteo;
        }else {
            return (this.windMeteo + this.windWeather) / 2;
        }
    }

    @Override
    public String toString() {
        return String.format(
                        "Meteo      : (Temp:%3.2fc, Humidity:%3s%%, Cloud:%3s%%, Wind:%3.2fm/s)\n" +
                        "OpenWeather: (Temp:%3.2fc, Humidity:%3s%%, Cloud:%3s%%, Wind:%3.2fm/s)\n" +
                        "Average    : (Temp:%3.2fc, Humidity:%3s%%, Cloud:%3s%%, Wind:%3.2fm/s)",
                this.tempMeteo,this.humidityMeteo, this.cloudMeteo, this.windMeteo,
                this.tempWeather, this.humidityWeather, this.cloudWeather, this.windWeather,
                tempAverage(), humanityAverage(), cloudAverage(), windAverage()
                );
    }
}
