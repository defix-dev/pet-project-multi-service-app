package ru.defix.chat.exception;

public class ChatAlreadyExistException extends RuntimeException{
    public ChatAlreadyExistException() {
        super("Chat already exist.");
    }
}
