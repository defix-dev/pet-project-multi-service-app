package ru.defix.calculator.operator.programmer;

import ru.defix.calculator.operator.Operator;

public class BitwiseXorOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return (long) a ^ (long) b;
    }
}
