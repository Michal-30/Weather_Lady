package org.example.connection.views.menuoptions;

public enum MainMenuOptions {
    LOCATIONS("Location"), WEATHER("Weather"), EXIT("Exit");

    private final String value;

    MainMenuOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
