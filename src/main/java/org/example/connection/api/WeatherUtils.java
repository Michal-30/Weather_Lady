package org.example.connection.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.connection.db.models.Weather;

public class WeatherUtils {
    private final Gson gson;

    public WeatherUtils() {
        this.gson = new Gson();
    }

    public Weather createWeather(String openMeteoResponse, String openWeatherResponse) {
        JsonObject openMeteoJsonObject = gson.fromJson(openMeteoResponse, JsonObject.class)
                .getAsJsonObject("current");
        JsonObject openWeatherMainJsonObject = gson.fromJson(openWeatherResponse, JsonObject.class)
                .getAsJsonObject("main");
        JsonObject openWeatherWindJsonObject = gson.fromJson(openWeatherResponse, JsonObject.class)
                .getAsJsonObject("wind");
        JsonObject openWeatherCloudsJsonObject = gson.fromJson(openWeatherResponse, JsonObject.class)
                .getAsJsonObject("clouds");

        double tempOpenMeteo = openMeteoJsonObject.get("temperature_2m").getAsDouble();
        int humidityOpenMeteo = openMeteoJsonObject.get("relative_humidity_2m").getAsInt();
        int cloudOpenMeteo = openMeteoJsonObject.get("cloud_cover").getAsInt();
        double windOpenMeteo = openMeteoJsonObject.get("wind_speed_10m").getAsDouble();

        double tempOpenWeather =  openWeatherMainJsonObject.get("temp").getAsDouble();
        int humidityOpenWeather =  openWeatherMainJsonObject.get("humidity").getAsInt();
        double windOpenWeather =  openWeatherWindJsonObject.get("speed").getAsDouble();
        int cloudOpenWeather =  openWeatherCloudsJsonObject.get("all").getAsInt();

        return new Weather(tempOpenMeteo, humidityOpenMeteo, cloudOpenMeteo, windOpenMeteo,
                tempOpenWeather, humidityOpenWeather, cloudOpenWeather, windOpenWeather
        );
    }
}
