package ru.defix.weather.api.facade;

import ru.defix.weather.service.LocationCache;
import ru.defix.weather.api.dto.response.DailyForecastApiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.defix.weather.service.client.WeatherApiClient;
import ru.defix.weather.exception.FailedToExecuteWeatherApiRequestException;
import ru.defix.weather.api.dto.response.CurrentWeatherApiDTO;
import ru.defix.weather.api.dto.response.HourlyWeatherApiDTO;

import java.util.List;

@Service
public class WeatherControllerFacade {
    private static final Logger logger = LoggerFactory.getLogger(WeatherControllerFacade.class);
    private final LocationCache locCache;
    private final WeatherApiClient apiClient;

    @Autowired
    public WeatherControllerFacade(LocationCache locCache,
                                   WeatherApiClient apiClient) {
        this.locCache = locCache;
        this.apiClient = apiClient;
    }

    public CurrentWeatherApiDTO getCurrentWeather(String countryName) {
        try {
            return apiClient.getCurrentWeatherData(locCache.getLocation(countryName));
        } catch (Exception e) {
            logger.error("Failed to get current weather data: {}", countryName);
            throw new FailedToExecuteWeatherApiRequestException(e.getMessage());
        }
    }

    public List<HourlyWeatherApiDTO> getDailyWeather(String countryName) {
        try {
            return apiClient.getDailyWeatherData(locCache.getLocation(countryName));
        } catch (Exception e) {
            logger.error("Failed to get daily weather data: {}", countryName);
            throw new FailedToExecuteWeatherApiRequestException(e.getMessage());
        }
    }

    public List<DailyForecastApiDTO> getForecastWeather(String countryName, int dayLimit) {
        try {
            return apiClient.getForecastWeatherData(locCache.getLocation(countryName), dayLimit);
        } catch (Exception e) {
            logger.error("Failed to get forecast weather data: {}", countryName);
            throw new FailedToExecuteWeatherApiRequestException(e.getMessage());
        }
    }
}
