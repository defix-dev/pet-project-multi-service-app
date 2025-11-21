package ru.defix.calculator.api.advice;

import ru.defix.util.ControllerAdviceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.defix.calculator.exception.*;

import java.util.Map;

public class CalculatorControllerAdvice {
    @ExceptionHandler(BadExpressionFormatException.class)
    public ResponseEntity<Map<String, Object>> handleBadExpressionFormatException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.BAD_REQUEST.value())
        );
    }

    @ExceptionHandler(CalculatorNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCalculatorNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.NOT_FOUND.value())
        );
    }

    @ExceptionHandler(EmptyExpressionException.class)
    public ResponseEntity<Map<String, Object>> handleEmptyExpressionException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.BAD_REQUEST.value())
        );
    }

    @ExceptionHandler(FunctionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleFunctionNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.BAD_REQUEST.value())
        );
    }

    @ExceptionHandler(TokenNotAllowedException.class)
    public ResponseEntity<Map<String, Object>> handleTokenNotAllowedException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.BAD_REQUEST.value())
        );
    }
}
