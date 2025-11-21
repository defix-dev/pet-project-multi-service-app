package ru.defix.chat.exception;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException() {
        super("Chat not found.");
    }
}
