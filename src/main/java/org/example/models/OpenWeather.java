package org.example.models;

import com.google.gson.annotations.SerializedName;

public class OpenWeather {
    @SerializedName("main") Main main;
    @SerializedName("clouds") Clouds clouds;

    public static class Main {
        @SerializedName("temp") double temperature;
        @SerializedName("feels_like") double feelsLike;
        @SerializedName("humidity") double humidity;
        @SerializedName("pressure") double pressure;
    }

    public static class Clouds {
        @SerializedName("all") double all;
    }

    public double getTemperature() {
        return main.temperature;
    }

    public double getFeelsLike() {
        return main.feelsLike;
    }

    public double getHumidity() {
        return main.humidity;
    }

    public double getPressure() {
        return main.pressure;
    }

    public double getClouds() {
        return clouds.all;
    }
}
