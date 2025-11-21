package ru.defix.currencyConverter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;

public class CurrencyConverter {
    public static float convert(ConverterOptions options) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest req = HttpRequest.newBuilder()
                .GET().uri(URI.create(
                        MessageFormat.format("https://cash.rbc.ru/cash/json/converter_currency_rate/?currency_from={0}&currency_to={1}&source=cbrf&sum={2}",
                                options.getFromType(), options.getToType(), options.getFromValue())
                )).build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(res.body());
        return (float) root.get("data").get("sum_result").asDouble();
    }
}
