package ru.defix.chat.exception;

public class MessageAccessDeniedException extends RuntimeException{
    public MessageAccessDeniedException() {
        super("Message access denied.");
    }
}
