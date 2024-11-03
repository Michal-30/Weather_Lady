package org.example.connection.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.connection.db.daos.GenericDao;
import org.example.connection.db.models.Weather;
import org.example.connection.db.services.GenericService;

public class WeatherApiService {
    private double lat;
    private double lon;
    private WeatherApiHttp weatherApiHttp;
    private final Gson gson;

    public WeatherApiService(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.weatherApiHttp = new WeatherApiHttp();
        this.gson = new Gson();
    }

    public Weather createWeathers() {
        String openMeteoResponse = weatherApiHttp.getOpenMeteoData(this.lat, this.lon);
        String openWeatherResponse = weatherApiHttp.getOpenWeatherData(this.lat, this.lon);

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


    public static void main(String[] args) {
        GenericDao<Weather, Long> weatherDao = new GenericDao<>(Weather.class);
        GenericService<Weather, Long> weatherService = new GenericService<>(weatherDao);

        WeatherApiService weatherApiService = new WeatherApiService(52.52, 13.41);

        weatherService.save(weatherApiService.createWeathers());
    }
}
