package ru.defix.calculator.exception;

public class CalculatorNotFoundException extends RuntimeException {
    public CalculatorNotFoundException() {
        super("Calculator not found.");
    }
}
