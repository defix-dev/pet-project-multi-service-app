package ru.defix.auth.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.defix.util.ControllerAdviceUtils;
import ru.defix.auth.exception.UsernameOrPasswordInvalidException;

import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameOrPasswordInvalidException(UsernameOrPasswordInvalidException e) {
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status).body(ControllerAdviceUtils.prepareResponseData(
                e, status
        ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException e) {
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status).body(ControllerAdviceUtils.prepareResponseData(
                e, status
        ));
    }
}
