package ru.defix.weather.api.controller;

import ru.defix.weather.api.facade.WeatherControllerFacade;
import ru.defix.weather.api.dto.response.CurrentWeatherApiDTO;
import ru.defix.weather.api.dto.response.DailyForecastApiDTO;
import ru.defix.weather.api.dto.response.HourlyWeatherApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for weather-related operations.
 * Provides endpoints for current weather, hourly weather, and forecast.
 */
@RestController
@RequestMapping("/api/v1/weather")
public class WeatherControllerV1 {
    private final WeatherControllerFacade apiFacade;

    /**
     * Constructs a WeatherControllerV1 with the given WeatherControllerFacade.
     *
     * @param apiFacade Facade to handle weather data operations.
     */
    @Autowired
    public WeatherControllerV1(WeatherControllerFacade apiFacade) {
        this.apiFacade = apiFacade;
    }

    /**
     * Returns the current weather for the given country.
     *
     * @param name The name of the country.
     * @return {@link CurrentWeatherApiDTO} containing weather details.
     */
    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CurrentWeatherApiDTO> getCurrentWeather(@RequestParam("countryName") String name) {
        return ResponseEntity.ok(apiFacade.getCurrentWeather(name));
    }

    /**
     * Returns hourly weather data for the given country (today).
     *
     * @param name The name of the country.
     * @return A list of {@link HourlyWeatherApiDTO} entries for the day.
     */
    @GetMapping("/daily")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<HourlyWeatherApiDTO>> getDailyWeather(@RequestParam("countryName") String name) {
        return ResponseEntity.ok(apiFacade.getDailyWeather(name));
    }

    /**
     * Returns forecast weather data for the given country and number of days.
     *
     * @param name     The name of the country.
     * @param dayLimit Number of days for the forecast.
     * @return A list of {@link DailyForecastApiDTO} entries.
     */
    @GetMapping("/forecast")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DailyForecastApiDTO>> getForecastWeather(@RequestParam("countryName") String name,
                                                                        @RequestParam("dayLimit") int dayLimit) {
        return ResponseEntity.ok(apiFacade.getForecastWeather(name, dayLimit));
    }
}