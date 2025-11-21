
package ru.defix.calculator.function.weird;

import ru.defix.calculator.function.Function;

public class LogSumFunction implements Function {
    public double calculate(double... args) {
        return Math.log10(sumOfDigits((int) args[0]));
    }

    private int sumOfDigits(int n) {
        return String.valueOf(Math.abs(n)).chars().map(Character::getNumericValue).sum();
    }
}
