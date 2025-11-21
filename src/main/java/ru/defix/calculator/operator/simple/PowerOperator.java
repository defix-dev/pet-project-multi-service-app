package ru.defix.calculator.operator.simple;

import ru.defix.calculator.operator.Operator;

public class PowerOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return Math.pow(a, b);
    }
}
