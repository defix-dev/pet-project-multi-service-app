package ru.defix.weather.exception;

public class FailedToExecuteWeatherApiRequestException extends RuntimeException {
    public FailedToExecuteWeatherApiRequestException(String message) {
        super("Failed to execute weather api request: "+message);
    }
}
