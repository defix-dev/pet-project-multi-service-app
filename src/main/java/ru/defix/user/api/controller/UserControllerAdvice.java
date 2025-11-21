package ru.defix.user.api.controller;

import ru.defix.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.defix.util.ControllerAdviceUtils;
import ru.defix.user.exception.UserAlreadyExistException;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ControllerAdviceUtils.prepareResponseData(
                        e, HttpStatus.CONTINUE.value()
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ControllerAdviceUtils.prepareResponseData(
                        e, HttpStatus.NOT_FOUND.value()
                )
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ControllerAdviceUtils.prepareResponseData(
                        e, HttpStatus.FORBIDDEN.value()
                )
        );
    }
}
