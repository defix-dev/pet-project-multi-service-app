package ru.defix.auth.exception;

public class UsernameOrPasswordInvalidException extends RuntimeException {
    public UsernameOrPasswordInvalidException() {
        super("Username or password is invalid.");
    }
}
