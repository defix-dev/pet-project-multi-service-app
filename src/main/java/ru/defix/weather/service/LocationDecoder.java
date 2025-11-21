package ru.defix.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.defix.weather.exception.FailedToDecodeLocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LocationDecoder {
    private final static Logger logger = LoggerFactory.getLogger(LocationDecoder.class);
    private final static String DECODE_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%d&appid=%s";
    private final static String DECODE_API_KEY = System.getenv("WEATHER_API_KEY");

    public static Location decode(String name) throws IOException, InterruptedException {
        if(DECODE_API_KEY == null) throw new IllegalArgumentException("Decode api key not found.");
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest req = HttpRequest.newBuilder().GET()
                .uri(URI.create(String.format(DECODE_API_URL, name, 1, DECODE_API_KEY)))
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        logger.debug("Current location response: {}", res.body());
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(res.body()).get(0);
            logger.debug("Current location: {}:{}", root.get("lat").asDouble(), root.get("lon").asDouble());
            return new Location(root.get("lat").asDouble(), root.get("lon").asDouble());
        } catch (Exception e) {
            throw new FailedToDecodeLocationException();
        }
    }
}
