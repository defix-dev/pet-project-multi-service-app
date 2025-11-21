package ru.defix.chat.exception;

public class SingleMemberChatException extends RuntimeException {
    public SingleMemberChatException() {
        super("Single member chat.");
    }
}
