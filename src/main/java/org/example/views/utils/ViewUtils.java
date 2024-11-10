package org.example.views.utils;

import org.beryx.textio.TextIO;
import org.example.db.models.Location;
import org.example.db.models.Weather;

import java.util.List;

public abstract class ViewUtils {
    public static void createWeatherOutput(TextIO textIO, Weather weather, Location location) {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println();

        String locationString = "Weather for: " + location.getCity() +
                ", " + location.getCounty() +
                ", " + location.getCountry();
        String openMeteoString = "Temperature: " + weather.getTempMeteo() +
                "°C, Wind: " + weather.getWindMeteo() +
                "m/s, Clouds: " + weather.getCloudMeteo() +
                "%, Humidity: " + weather.getHumidityMeteo() + "%";
        String openWeatherString = "Temperature: " + weather.getTempWeather() +
                "°C, Wind: " + weather.getWindWeather() +
                "m/s, Clouds: " + weather.getCloudWeather() +
                "%, Humidity: " + weather.getHumidityWeather() + "%";
        String avarageString = String.format("Temperature: %.2f°C, Wind: %.2f m/s, Clouds: %d%%, Humidity: %d%%",
                (weather.getTempWeather() + weather.getTempMeteo()) / 2,
                (weather.getWindWeather() + weather.getWindMeteo()) / 2,
                (weather.getCloudWeather() + weather.getCloudMeteo()) / 2,
                (weather.getHumidityWeather() + weather.getHumidityMeteo()) / 2);

        textIO.getTextTerminal().println(locationString);
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("OpenMeteo api:");
        textIO.getTextTerminal().println(openMeteoString);
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("OpenWeather api:");
        textIO.getTextTerminal().println(openWeatherString);
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Average:");
        textIO.getTextTerminal().println(avarageString);
        textIO.getTextTerminal().println();
    }
}
