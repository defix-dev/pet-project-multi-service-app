package ru.defix.chat.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.defix.util.ControllerAdviceUtils;
import ru.defix.chat.exception.*;

import java.util.Map;

@RestControllerAdvice
public class ChatControllerAdvice {
    @ExceptionHandler(ChatAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleChatAlreadyExistException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.CONFLICT.value())
        );
    }

    @ExceptionHandler(ChatKeysAlreadyCreatedException.class)
    public ResponseEntity<Map<String, Object>> handleChatKeysAlreadyCreatedException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.CONFLICT.value())
        );
    }

    @ExceptionHandler(ChatKeysNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleChatKeysNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.NOT_FOUND.value())
        );
    }

    @ExceptionHandler(EmptyChatsException.class)
    public ResponseEntity<Map<String, Object>> handleEmptyChatsException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.NOT_FOUND.value())
        );
    }

    @ExceptionHandler(MessageAccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleMessageAccessDeniedException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.FORBIDDEN.value())
        );
    }

    @ExceptionHandler(NoMessagesException.class)
    public ResponseEntity<Map<String, Object>> handleNoMessagesException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.NOT_FOUND.value())
        );
    }

    @ExceptionHandler(SingleMemberChatException.class)
    public ResponseEntity<Map<String, Object>> handleSingleMemberChatException(RuntimeException e) {
        return ResponseEntity.badRequest().body(
                ControllerAdviceUtils.prepareResponseData(e, HttpStatus.BAD_REQUEST.value())
        );
    }
}
