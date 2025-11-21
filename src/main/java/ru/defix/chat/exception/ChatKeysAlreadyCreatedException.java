package ru.defix.chat.exception;

public class ChatKeysAlreadyCreatedException extends RuntimeException {
    public ChatKeysAlreadyCreatedException() {
        super("Chat keys already created.");
    }
}
