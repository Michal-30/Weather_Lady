package org.example.connection.views;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.example.connection.api.services.LocationService;
import org.example.connection.views.menuoptions.ApplicationMenu;
import org.example.connection.views.menuoptions.MainMenuOptions;

public class MainView implements ApplicationMenu {
    private final WeatherView weatherView;
    private final TextIO textIO;


    public MainView(TextIO textIO, WeatherView weatherView) {
        this.textIO = textIO;
        this.weatherView = weatherView;
    }

    @Override
    public void showMenu() {
        textIO.getTextTerminal().println("Main menu:");

        MainMenuOptions selectedOption = null;
        while (selectedOption != MainMenuOptions.EXIT) {
            selectedOption = textIO.newEnumInputReader(MainMenuOptions.class)
                    .withAllValuesNumbered()
                    .withDefaultValue(MainMenuOptions.EXIT)
                    .withValueFormatter(MainMenuOptions::getValue)
                    .read("Choose an option: ");

            switch (selectedOption) {
                case CHECK -> weatherView.showMenu();
                case EXIT -> textIO.dispose();
            }
        }
    }

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();

        WeatherView weatherView = new WeatherView(textIO);
        MainView mainView = new MainView(textIO, weatherView);

        mainView.showMenu();
    }
}
