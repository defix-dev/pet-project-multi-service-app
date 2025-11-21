package ru.defix.chat.exception;

public class NoMessagesException extends RuntimeException {
    public NoMessagesException() {
        super("No messages.");
    }
}
