package ru.defix.weather.api.advice;

import ru.defix.weather.exception.FailedToDecodeLocationException;
import ru.defix.weather.exception.FailedToExecuteWeatherApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.defix.util.ControllerAdviceUtils;

import java.util.Map;

@RestController
public class WeatherControllerAdvice {
    @ExceptionHandler(FailedToDecodeLocationException.class)
    public ResponseEntity<Map<String, Object>> handleFailedToDecodeLocationException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ControllerAdviceUtils.prepareResponseData(
                        e, HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }

    @ExceptionHandler(FailedToExecuteWeatherApiRequestException.class)
    public ResponseEntity<Map<String, Object>> handleFailedToExecuteWeatherApiRequestException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ControllerAdviceUtils.prepareResponseData(
                        e, HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }
}
