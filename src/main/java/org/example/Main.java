package org.example;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.example.views.MainView;
import org.example.views.WeatherView;

public class Main {
    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();

        WeatherView weatherView = new WeatherView(textIO);
        MainView mainView = new MainView(textIO, weatherView);

        mainView.showMenu();
    }
}