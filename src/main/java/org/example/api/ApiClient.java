package org.example.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.models.GeoCodeResponse;
import org.example.models.OpenApiResponse;
import org.example.models.OpenMeteo;
import org.example.models.OpenWeather;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiClient {
    
    static Dotenv dotenv = Dotenv.load();

    private final HttpClient client;
    private final Gson gson;

    public ApiClient() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    HttpRequest newRequest(final String url, @SuppressWarnings("SameParameterValue") final String method) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();
    }

    HttpRequest newRequest(final String url, final String method, HttpRequest.BodyPublisher bodyPublisher) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(method, bodyPublisher)
                .build();
    }

    <R> R getResponse(HttpRequest request, @SuppressWarnings("SameParameterValue") Class<R> type) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) throw new IOException("Unexpected response code: " + response.statusCode());
            return gson.fromJson(response.body(), type);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }


    <V> V getResponse(HttpRequest request, Type type, @SuppressWarnings("SameParameterValue") Class<V> clazz) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) throw new IOException("Unexpected response code: " + response.statusCode());
            return gson.fromJson(response.body(), type);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }


    @SuppressWarnings("unused")
    @Deprecated()
    public GeoCodeResponse fetchGeoCodeResponses(final double lon, final double lat) {

        final String url = String.format("https://geocode.maps.co/reverse?lat=%f&lon=%f&api_key=%s", lat, lon, dotenv.get("GEO_CODE_API_KEY"));
        var request = newRequest(url, "GET");
        return getResponse(request, GeoCodeResponse.class);
    }

    @SuppressWarnings("unchecked")
    public List<GeoCodeResponse> fetchGeoCodeResponses(final String search) {
        final String url = String.format("https://geocode.maps.co/search?q=%s&api_key=%s", enc(search), dotenv.get("GEO_CODE_API_KEY"));

        var request = newRequest(url, "GET");
        List<GeoCodeResponse>  resp = getResponse(request, GeoCodeResponse.GEO_CODE_LISTS, List.class);
        resp.forEach(GeoCodeResponse::afterDeserialization);
        return resp;
    }

    // Weather{tempM, humidityM, cloudM, windM, tempW, humidityW, cloudW, windW}
    // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,relative_humidity_2m,cloud_cover,wind_speed_10m&timezone=Europe%2FBerlin -MT
    // https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&units=metric&appid=ec7b93991f85cb151e6257361ef3543e - WT


    @SuppressWarnings("unchecked")
    public OpenApiResponse fetchOpenApiResponses(final GeoCodeResponse.Coordinates coordinates) {
        final String meteo = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m,relative_humidity_2m,cloud_cover,wind_speed_10m",
                (coordinates.lat() + "").replaceAll(",", "."),
                (coordinates.lon() + "").replaceAll(",", ".")
        ) + "&timezone=Europe%2FBerlin";


        final String weather = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s",
                coordinates.lat(), coordinates.lon(), dotenv.get("OPEN_WEATHER_API_KEY")
        );

        OpenMeteo meteoResponse;

        try {
            List<OpenMeteo> meteoResponseList = getResponse(newRequest(meteo, "GET"), OpenMeteo.OPEN_METEO_LIST, List.class);
            meteoResponse = meteoResponseList.getFirst();
        } catch (JsonSyntaxException e) {
            meteoResponse = getResponse(newRequest(meteo, "GET"), OpenMeteo.class);
        }

        var weatherResponse = getResponse(newRequest(weather, "GET"), OpenWeather.class);

        return new OpenApiResponse(meteoResponse, weatherResponse);
    }


    String enc(final String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

}
