package org.example.connection.views;

import org.beryx.textio.TextIO;
import org.example.connection.views.menuoptions.ApplicationMenu;
import org.example.connection.views.menuoptions.WeatherMenu;

public class WeatherView implements ApplicationMenu {
    private TextIO textIO;

    public WeatherView(TextIO textIO) {
        this.textIO = textIO;
    }


    @Override
    public void showMenu() {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Locations menu:");

        WeatherMenu selectedOption = null;
        while (selectedOption != WeatherMenu.BACK) {
            selectedOption = textIO.newEnumInputReader(WeatherMenu.class)
                    .withAllValuesNumbered()
                    .withDefaultValue(WeatherMenu.BACK)
                    .withValueFormatter(WeatherMenu::getValue)
                    .read("Choose an option: ");

            switch (selectedOption) {
                case ADD -> textIO.getTextTerminal().println("add");
                case HISTORICAL_DATA -> textIO.getTextTerminal().println("historical");
                case BACK -> textIO.getTextTerminal().println("Back to main menu");
            }
        }
    }
}
