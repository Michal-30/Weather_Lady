package org.example.views.menuoptions;

public enum WeatherMenu {
    ADD("Get weather information's."),
    HISTORICAL_DATA("Get historical weather information's"),

    BACK("Back");

    private final String value;

    WeatherMenu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
