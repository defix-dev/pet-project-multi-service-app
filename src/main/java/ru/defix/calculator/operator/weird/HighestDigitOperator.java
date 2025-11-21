package ru.defix.calculator.operator.weird;

import ru.defix.calculator.operator.Operator;

public class HighestDigitOperator implements Operator {
    public double calculate(double a, double b) {
        return highestDigit((int) a);
    }

    private int highestDigit(int n) {
        return (int) Math.pow(10, (int) Math.log10(n));
    }
}
