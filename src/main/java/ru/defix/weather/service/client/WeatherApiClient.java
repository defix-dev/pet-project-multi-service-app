package ru.defix.weather.service.client;

import ru.defix.weather.api.dto.response.CurrentWeatherApiDTO;
import ru.defix.weather.api.dto.response.DailyForecastApiDTO;
import ru.defix.weather.api.dto.response.HourlyWeatherApiDTO;
import ru.defix.weather.service.Location;

import java.io.IOException;
import java.util.List;

public interface WeatherApiClient {
    CurrentWeatherApiDTO getCurrentWeatherData(Location location) throws IOException, InterruptedException;
    List<HourlyWeatherApiDTO> getDailyWeatherData(Location location) throws IOException, InterruptedException;
    List<DailyForecastApiDTO> getForecastWeatherData(Location location, int dayLimit) throws IOException, InterruptedException;
}
