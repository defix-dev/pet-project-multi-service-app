package ru.defix.calculator.exception;

public class EmptyExpressionException extends RuntimeException {
    public EmptyExpressionException() {
        super("Empty expression.");
    }
}
