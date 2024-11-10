package org.example.views.utils;

import org.beryx.textio.TextIO;
import org.example.db.models.Location;
import org.example.db.models.Weather;

import java.time.LocalDateTime;
import java.util.List;

public abstract class ViewUtils {
    public static void createWeatherOutput(TextIO textIO, Weather weather, Location location) {
        LocalDateTime localDateTime = weather.getTime();
        String date = String.format("%s.%s.%s - %s:%s", localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute());
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println(date +": "+location.toString());
        textIO.getTextTerminal().println(weather.toString());
        textIO.getTextTerminal().println();
    }
}
