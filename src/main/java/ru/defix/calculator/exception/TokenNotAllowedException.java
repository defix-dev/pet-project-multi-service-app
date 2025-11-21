package ru.defix.calculator.exception;

public class TokenNotAllowedException extends Exception {
    public TokenNotAllowedException() {
        super("Token not allowed.");
    }
}
