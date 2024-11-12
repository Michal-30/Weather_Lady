package org.example.models;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class OpenApiResponse {
    private final OpenMeteo openMeteo;
    private final OpenWeather openWeather;

    private final LocalDateTime date;
    private final double temperature;
    private final double pressure;
    private final double humidity;
    private final double windSpeed;
    private final double clouds;


    public OpenApiResponse(OpenMeteo openMeteo, OpenWeather openWeather) {
        this.openMeteo = openMeteo;
        this.openWeather = openWeather;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        this.date = LocalDateTime.parse(openMeteo.getTime(), formatter);
        this.temperature = Math.max(openMeteo.getTemperature(), openWeather.getTemperature());
        this.pressure = openWeather.getPressure();
        this.humidity = Math.max(openMeteo.getHumidity(), openWeather.getHumidity());
        this.windSpeed = openMeteo.getWindSpeed();
        this.clouds = Math.max(openMeteo.getClouds(), openWeather.getClouds());
    }
}
