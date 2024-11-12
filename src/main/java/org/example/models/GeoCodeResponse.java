package org.example.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.List;

public class GeoCodeResponse {

    @Setter
    @SerializedName("display_name")
    private String displayName;

    @SerializedName("address")
    @Getter private Address address;

    @SerializedName("lat")
    private double latitude;

    @SerializedName("lon")
    private double longitude;

    @Getter
    private Coordinates coordinates;

    public static Type GEO_CODE_LISTS = new TypeToken<List<GeoCodeResponse>>(){}.getType();

    public void afterDeserialization() {
        this.coordinates = new Coordinates(latitude, longitude);
        var chunks = this.displayName.split(",");

        this.address = new Address(
                chunks[0].trim(),
                chunks[1].trim(),
                chunks[2].trim(),
                "",
                chunks[chunks.length -1].trim()
        );
    }

    public record Coordinates(double lat, double lon) {}
    public record Address(
            String suburb,
            String municipality,
            String state,
            @SerializedName("postcode") String postCode,
            @SerializedName("country") String country
    ){

        @Override
        public String toString() {
            return "Address{" + "suburb='" + suburb + '\'' +
                    ", municipality='" + municipality + '\'' +
                    ", state='" + state + '\'' +
                    ", postCode='" + postCode + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }

    @Deprecated
    public void mutate(GeoCodeResponse geoCodeResponse) {
        if (geoCodeResponse.address != null) {
            this.address = geoCodeResponse.address;
        }
    }

    @Override
    public String toString() {
        return "GeoCodeResponse{" + "displayName='" + displayName + '\'' +
                ", address=" + address +
                ", coordinates=" + coordinates +
                '}';
    }
}
