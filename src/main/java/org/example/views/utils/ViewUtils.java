package org.example.views.utils;

import org.beryx.textio.TextIO;
import org.example.db.models.Location;
import org.example.db.models.Weather;

import java.util.List;

public abstract class ViewUtils {
    public static void createWeatherOutput(TextIO textIO, Weather weather, Location location) {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println(weather.toString());
        textIO.getTextTerminal().println();
    }
}
