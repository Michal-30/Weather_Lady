package org.example.models;

public record Coordinates(double latitude, double longitude) {
    @Override
    public String toString() {
        return "Coordinates{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
