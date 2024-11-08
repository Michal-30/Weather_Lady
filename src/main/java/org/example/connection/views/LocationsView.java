package org.example.connection.views;

import org.beryx.textio.TextIO;
import org.example.connection.views.menuoptions.ApplicationMenu;
import org.example.connection.views.menuoptions.LocationsMenu;

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

    }
}
