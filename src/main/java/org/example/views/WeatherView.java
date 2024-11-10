package org.example.views;

import org.beryx.textio.TextIO;
import org.example.db.models.Location;
import org.example.db.models.Weather;
import org.example.views.menuoptions.ApplicationMenu;
import org.example.views.services.ViewService;
import org.example.views.utils.ViewUtils;

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

        Weather weather = viewService.createWeather(locationToDb);


        ViewUtils.createWeatherOutput(textIO, weather, locationToDb);

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
            ViewUtils.createWeatherOutput(textIO, w, l);
        }
    }

    private int choiceInput(List<Location> locationList) {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println();

        if (locationList.isEmpty()) {
            textIO.getTextTerminal().println("No result was received.");

            return -1;
        }

        for (int i = 0; i < locationList.size(); i++) {
            Location location = locationList.get(i);
            textIO.getTextTerminal().println((i + 1) + ": " + location.toString());
            if (i == locationList.size() - 1) {
                textIO.getTextTerminal().println((i + 2) + ": Back");
            }
        }

        return textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(locationList.size() + 1)
                .withDefaultValue(locationList.size() + 1)
                .read("Choose an option:");
    }

    private String searchedLocationInput() {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println();

        return textIO.newStringInputReader()
                .read("Search for a location");
    }
}