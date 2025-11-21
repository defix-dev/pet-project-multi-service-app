package ru.defix.calculator.exception;

public class FunctionNotFoundException extends RuntimeException {
    public FunctionNotFoundException() {
        super("Function not found.");
    }
}
