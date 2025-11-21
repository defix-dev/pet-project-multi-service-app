package ru.defix.tests.controller;

import ru.defix.tests.TestUtils;
import ru.defix.weather.api.facade.WeatherControllerFacade;
import ru.defix.weather.api.dto.response.CurrentWeatherApiDTO;
import ru.defix.weather.api.dto.response.DailyForecastApiDTO;
import ru.defix.weather.api.dto.response.HourlyWeatherApiDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
@Disabled
public class WeatherControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherControllerFacade weatherApiFacade;

    @Nested
    class SuccessfulCases {
        @Test
        @WithMockUser
        public void getCurrentWeatherTest() throws Exception {
            when(weatherApiFacade.getCurrentWeather("Moscow")).thenReturn(
                    new CurrentWeatherApiDTO(
                            1, 2, 3, 4
                    )
            );

            String expectedJson = """
                    {
                        "temperature":1,
                        "windSpeed":2,
                        "humidityIntensive":3,
                        "pressure":4
                    }
                    """;

            mockMvc.perform(get("/api/v1/weather/current")
                            .param("countryName", "Moscow")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }

        @Test
        @WithMockUser
        public void getDailyWeatherTest() throws Exception {
            HourlyWeatherApiDTO expectedOutput = new HourlyWeatherApiDTO(
                    LocalTime.now(), 1, 2
            );
            when(weatherApiFacade.getDailyWeather("Moscow")).thenReturn(List.of(expectedOutput));

            String expectedJson = String.format("""
                            [{
                                "temperature":%f,
                                "time":"%s",
                                "state":%d
                            }]
                            """, expectedOutput.getTemperature(),
                    TestUtils.localTimeToString(expectedOutput.getTime()),
                    expectedOutput.getState());

            mockMvc.perform(get("/api/v1/weather/daily")
                            .param("countryName", "Moscow")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }

        @Test
        @WithMockUser
        public void getForecastWeatherTest() throws Exception {
            DailyForecastApiDTO expectedOutput = new DailyForecastApiDTO(
                    LocalDate.now(), 1, 2, 3
            );
            when(weatherApiFacade.getForecastWeather("Moscow", 1)).thenReturn(List.of(expectedOutput));

            String expectedJson = String.format("""
                    [{
                        "dayTemperature":%f,
                        "nightTemperature":%f,
                        "date":"%s",
                        "state":%d
                    }]
                    """, expectedOutput.getDayTemperature(),
                    expectedOutput.getNightTemperature(),
                    expectedOutput.getDate(),
                    expectedOutput.getState());

            mockMvc.perform(get("/api/v1/weather/forecast")
                            .param("countryName", "Moscow")
                            .param("dayLimit", "1")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    class AuthErrorCases {
        @Test
        public void getCurrentWeatherIfUnauthorizedTest() throws Exception {
            mockMvc.perform(get("/api/v1/weather/current")
                            .param("countryName", "Moscow")
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getDailyWeatherIfUnauthorizedTest() throws Exception {
            mockMvc.perform(get("/api/v1/weather/daily")
                            .param("countryName", "Moscow")
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getForecastWeatherIfUnauthorizedTest() throws Exception {
            mockMvc.perform(get("/api/v1/weather/forecast")
                            .param("countryName", "Moscow")
                            .param("dayLimit", "1")
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class ParamErrorCases {
        @Test
        @WithMockUser
        public void getCurrentWeatherIfParamNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/weather/current")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        public void getDailyWeatherIfParamNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/weather/daily")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        public void getForecastWeatherIfParamNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/weather/forecast")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }
}
