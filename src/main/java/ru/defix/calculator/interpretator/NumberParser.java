package ru.defix.calculator.interpretator;

public class NumberParser {
    public static double parseNumber(String rawNumber) {
        rawNumber = rawNumber.replace(",", ".");
        return Double.parseDouble(rawNumber);
    }
}
