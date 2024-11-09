package org.example.connection.views;

import org.beryx.textio.TextIO;
import org.example.connection.api.services.LocationService;
import org.example.connection.db.models.Location;
import org.example.connection.views.menuoptions.ApplicationMenu;
import org.example.connection.views.menuoptions.LocationsMenu;

import java.util.List;

public class LocationsView implements ApplicationMenu {
    private TextIO textIO;

    public LocationsView(TextIO textIO) {
        this.textIO = textIO;
    }

    @Override
    public void showMenu() {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Locations menu:");

        LocationsMenu selectedOption = null;
        while (selectedOption != LocationsMenu.BACK) {
            selectedOption = textIO.newEnumInputReader(LocationsMenu.class)
                    .withAllValuesNumbered()
                    .withDefaultValue(LocationsMenu.BACK)
                    .withValueFormatter(LocationsMenu::getValue)
                    .read("Choose an option: ");

            switch (selectedOption) {
                case ADD -> this.addLocation();
                case LIST_ALL -> textIO.getTextTerminal().println("list");
                case REMOVE -> textIO.getTextTerminal().println("del");
                case BACK -> textIO.getTextTerminal().println("Back to main menu");
            }
        }
    }

    private void addLocation() {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Add location:");

        String searchedLocation = textIO.newStringInputReader()
                .read("Search a location");

        LocationService locationService = new LocationService(searchedLocation);

        List<Location> locationList = locationService.getCityLocationList();

        textIO.getTextTerminal().println();

        for (int i = 0; i < locationList.size(); i++) {
            Location location = locationList.get(i);
            textIO.getTextTerminal().println((i + 1) + ": " + location.getCity() + ", " + location.getCounty() + ", " + location.getCountry());
            if (i == locationList.size() - 1) {
                textIO.getTextTerminal().println((i + 2) + " Back");
            }
        }

        int choice = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(locationList.size() + 1)
                .withDefaultValue(locationList.size() + 1)
                .read("Choose an option:");

        if (choice == locationList.size() + 1) return;

        Location locationToDb = locationList.get(choice - 1);
        locationService.saveToDB(locationToDb);
    }
}
