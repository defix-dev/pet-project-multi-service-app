package ru.defix.weather.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyForecastApiDTO {
    private LocalDate date;
    private int state;
    private double dayTemperature;
    private double nightTemperature;
}
