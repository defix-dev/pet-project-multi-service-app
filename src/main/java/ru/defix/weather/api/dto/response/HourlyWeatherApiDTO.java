package ru.defix.weather.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HourlyWeatherApiDTO {
    private LocalTime time;
    private double temperature;
    private int state;
}
