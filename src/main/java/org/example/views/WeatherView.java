package org.example.views;

import org.beryx.textio.TextIO;
import org.example.db.models.Location;
import org.example.db.models.Weather;
import org.example.views.menuoptions.ApplicationMenu;
import org.example.views.services.ViewService;

import java.time.LocalDateTime;
import java.util.List;

public class WeatherView implements ApplicationMenu {
    private final TextIO textIO;
    private final ViewService viewService;

    public WeatherView(TextIO textIO) {
        this.textIO = textIO;
        this.viewService = new ViewService();
    }

    @Override
    public void showMenu() {
        String searchedLocation = this.searchedLocationInput();

        List<Location> locationList = viewService.search(searchedLocation);

        int choice = this.choiceInput(locationList);
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println();

        if (choice == locationList.size() + 1 || choice == -1) return;

        Location locationToDb = this.viewService.getCorrectLocation(locationList.get(choice - 1));

        Weather weather = new Weather(locationToDb);

        createWeatherOutput(textIO, weather, locationToDb);

        boolean isSave = textIO.newBooleanInputReader()
                .withDefaultValue(false)
                .withTrueInput("y")
                .withFalseInput("n")
                .read("Do you want to save this result?");

        if (isSave) {
            viewService.save(weather);
            textIO.getTextTerminal().println("Data was saved.");
        }

        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println();

        boolean isShowSavedData = textIO.newBooleanInputReader()
                .withDefaultValue(false)
                .withTrueInput("y")
                .withFalseInput("n")
                .read("Do you want to show saved data for this location?");

        if (isShowSavedData) {
            this.showSavedData(locationToDb);
        }
    }

    private void showSavedData(Location location) {
        List<Weather> savedData = this.viewService.getDataForLocation(location);

        for (Weather w : savedData) {
            Location l = w.getLocation();
            createWeatherOutput(textIO, w, l);
        }
    }

    private int choiceInput(List<Location> locationList) {
        final String RESET = "\u001B[0m";
        final String HEADER_COLOR = "\u001B[36m";
        final String DATA_COLOR = "\u001B[33m";
        final String BACK_COLOR = "\u001B[31m";

        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println();

        if (locationList.isEmpty()) {
            textIO.getTextTerminal().println(HEADER_COLOR + "No result was received." + RESET);
            return -1;
        }

        textIO.getTextTerminal().println(HEADER_COLOR + String.format("%-5s %-30s %-20s %-20s %-20s",
                "ID", "City Name", "Latitude", "Longitude", "Country Name") + RESET);
        textIO.getTextTerminal().println(HEADER_COLOR + "--------------------------------------------------------------------------------------------------------" + RESET);

        for (int i = 0; i < locationList.size(); i++) {
            Location location = locationList.get(i);

            textIO.getTextTerminal().println(DATA_COLOR + String.format("%-5d %-30s %-20.2f %-20.2f %-20s",
                    (i + 1),
                    location.getCity(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getMunicipality()+ RESET));
        }

        textIO.getTextTerminal().println(HEADER_COLOR + "--------------------------------------------------------------------------------------------------------" + RESET);
        textIO.getTextTerminal().println(BACK_COLOR + String.format("%-5d %-30s", locationList.size() + 1, "Back") + RESET);

        return textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(locationList.size() + 1)
                .withDefaultValue(locationList.size() + 1)
                .read(HEADER_COLOR + "Choose an option:" + RESET);
    }



    private String searchedLocationInput() {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println();

        return textIO.newStringInputReader()
                .read("Search for a location");
    }

    void createWeatherOutput(TextIO textIO, Weather weather, Location location) {
        final String RESET = "\u001B[0m";
        final String HEADER_COLOR = "\u001B[36m"; // Světle modrá pro nadpisy
        final String LABEL_COLOR = "\u001B[33m"; // Žlutá pro popisky
        final String DATA_COLOR = "\u001B[32m"; // Zelená pro hodnoty

        LocalDateTime localDateTime = weather.getTime();
        String date = String.format("%04d-%02d-%02d %02d:%02d",
                localDateTime.getYear(),
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getHour(),
                localDateTime.getMinute());

        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println(HEADER_COLOR + "Weather Data for Location:" + RESET);
        textIO.getTextTerminal().println(HEADER_COLOR + "-----------------------------------------------------------------------------------------------------" + RESET);

        // Datum a čas
        textIO.getTextTerminal().println(LABEL_COLOR + String.format("Date & Time: %s", RESET + DATA_COLOR + date + RESET));

        textIO.getTextTerminal().println(LABEL_COLOR + String.format("Location: %s, %s, %s, %s" + RESET,
                DATA_COLOR + location.getCity() + RESET,
                DATA_COLOR + location.getSuburb() + RESET,
                DATA_COLOR + location.getCountry() + RESET,
                DATA_COLOR + location.getMunicipality() + RESET));

        textIO.getTextTerminal().println(HEADER_COLOR + "-----------------------------------------------------------------------------------------------------" + RESET);

        textIO.getTextTerminal().println(HEADER_COLOR + String.format("%-20s %-20s %-20s %-20s" + RESET,
                "Temperature (°C)", "Humidity (%)", "Cloud Cover (%)", "Wind Speed (m/s)"));
        textIO.getTextTerminal().println(HEADER_COLOR + "-----------------------------------------------------------------------------------------------------" + RESET);

        textIO.getTextTerminal().println(String.format("%s%-20.2f%s %s%-20d%s %s%-20d%s %s%-20.2f%s",
                DATA_COLOR,weather.getTemp(),RESET,
                DATA_COLOR,weather.getHumidity(), RESET,
                DATA_COLOR,weather.getCloud(),RESET,
                DATA_COLOR,weather.getWind(),RESET));

        textIO.getTextTerminal().println();
    }

}