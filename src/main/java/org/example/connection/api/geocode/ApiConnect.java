package org.example.connection.api.geocode;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ApiConnect {
    private String url;
    private String jsonString;

    public ApiConnect(String url) {
        this.url = url;
        this.jsonString = jsonString();
    }

    public String getJsonString() {
        return jsonString;
    }

    private String jsonString() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(this.url);
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                //sleep for 1 second (free api give request every one second)
                Thread.sleep(1000);
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}