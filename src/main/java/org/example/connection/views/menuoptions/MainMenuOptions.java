package org.example.connection.views.menuoptions;

public enum MainMenuOptions {
    CHECK("Check weather"), EXIT("Exit");

    private final String value;

    MainMenuOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
