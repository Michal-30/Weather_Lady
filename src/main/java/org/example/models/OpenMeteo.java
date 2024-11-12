package org.example.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class OpenMeteo {

    public static Type OPEN_METEO_LIST = new TypeToken<List<OpenMeteo>>(){}.getType();

    @SerializedName( "current")
    Current current;

    public record Current(
        String time,
        @SerializedName("temperature_2m") double temperature,
        @SerializedName("relative_humidity_2m") double humidity,
        @SerializedName("cloud_cover") double clouds,
        @SerializedName("wind_speed_10m") double windSpeed
    ){}

    public String getTime() {
        return current.time;
    }

    public double getTemperature() {
        return current.temperature;
    }

    public double getHumidity() {
        return current.humidity;
    }

    public double getClouds() {
        return current.clouds;
    }

    public double getWindSpeed() {
        return current.windSpeed;
    }
}
