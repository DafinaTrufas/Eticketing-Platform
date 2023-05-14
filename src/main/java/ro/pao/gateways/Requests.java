package ro.pao.gateways;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.pao.model.Holiday;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class Requests {

    private static HttpClient httpClient = HttpClient.newHttpClient();

    private static ObjectMapper objectMapper = new ObjectMapper();

    public void saveRequestInfo() {

        try {

            HttpRequest httpRequestHolidays = HttpRequest.newBuilder()
                    .uri(new URI("https://holidays.abstractapi.com/v1/?api_key=335f82d1890d44c0bf17bdfd1a039121&country=US&year=2020&"))
                    .GET()
                    .build();

            var httpResponseHolidays = httpClient.send(httpRequestHolidays, HttpResponse.BodyHandlers.ofString());

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            var body = httpResponseHolidays.body();

            JsonNode holiday = (objectMapper.readTree(body)).get(0);

            System.out.println("name = " + holiday.get("name").asText());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
