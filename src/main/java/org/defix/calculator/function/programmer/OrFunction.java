package org.defix.calculator.function.programmer;

import org.defix.calculator.function.Function;

public class OrFunction implements Function {
    @Override
    public double calculate(double... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("or requires exactly two arguments");
        }
        return (long) args[0] | (long) args[1];
    }
}
