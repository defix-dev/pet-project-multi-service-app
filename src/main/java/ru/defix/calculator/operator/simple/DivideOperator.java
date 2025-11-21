package ru.defix.calculator.operator.simple;

import ru.defix.calculator.operator.Operator;

public class DivideOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return a / b;
    }
}
