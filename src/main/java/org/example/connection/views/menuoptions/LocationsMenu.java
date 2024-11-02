package org.example.connection.views.menuoptions;

public enum LocationsMenu {
    ADD("Add location"),
    LIST_ALL("List all locations"),
    REMOVE("Delete a location"),
    BACK("Back");

    private final String value;

    LocationsMenu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
