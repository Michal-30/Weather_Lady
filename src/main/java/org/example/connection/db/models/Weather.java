package org.example.connection.db.models;

import lombok.Setter;

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

    @Override
    public String toString() {
        return String.format(
                        "Meteo      : (Temp: %s, Humidity: %s, Cloud: %s, Wind: %s)\n" +
                        "OpenWeather: (Temp: %s, Humidity: %s, Cloud: %s, Wind: %s)",
                this.tempMeteo,this.humidityMeteo, this.cloudMeteo, this.windMeteo, this.tempWeather, this.humidityWeather, this.humidityWeather, this.windWeather);
    }
}
