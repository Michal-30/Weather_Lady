package org.example.api.meteo;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.db.models.Weather;

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

        JsonElement elementTempOpenMeteo = openMeteoJsonObject.get("temperature_2m");
        Double tempOpenMeteo = doubleElement(elementTempOpenMeteo);
        JsonElement elementHumidityOpenMeteo = openMeteoJsonObject.get("relative_humidity_2m");
        Integer humidityOpenMeteo = integerElement(elementHumidityOpenMeteo);
        JsonElement elementCloudOpenMeteo = openMeteoJsonObject.get("cloud_cover");
        Integer cloudOpenMeteo = integerElement(elementCloudOpenMeteo);
        JsonElement elementWindOpenMeteo = openMeteoJsonObject.get("wind_speed_10m");
        Double windOpenMeteo = doubleElement(elementWindOpenMeteo);


        JsonElement elementTempOpenWeather =  openWeatherMainJsonObject.get("temp");
        Double tempOpenWeather = doubleElement(elementTempOpenWeather);
        JsonElement elementHumidityOpenWeather =  openWeatherMainJsonObject.get("humidity");
        Integer humidityOpenWeather = integerElement(elementHumidityOpenWeather);
        JsonElement elementWindOpenWeather =  openWeatherWindJsonObject.get("speed");
        Integer cloudOpenWeather = integerElement(elementWindOpenWeather);
        JsonElement elementCloudOpenWeather =  openWeatherCloudsJsonObject.get("all");
        Double windOpenWeather = doubleElement(elementCloudOpenWeather);

        return new Weather(tempOpenMeteo, humidityOpenMeteo, cloudOpenMeteo, windOpenMeteo,
                tempOpenWeather, humidityOpenWeather, cloudOpenWeather, windOpenWeather
        );
    }

    private Double doubleElement(JsonElement jsonElement){
        if(!jsonElement.getAsString().isEmpty()){
            return jsonElement.getAsDouble();
        }else{
            return null;
        }
    }

    private Integer integerElement(JsonElement jsonElement){
        if(!jsonElement.getAsString().isEmpty()){
            return jsonElement.getAsInt();
        }else{
            return null;
        }
    }

}
