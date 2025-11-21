package ru.defix.calculator.function.weird;

import ru.defix.calculator.function.Function;

public class IsPalindromeFunction implements Function {
    public double calculate(double... args) {
        int n = (int) args[0];
        String s = String.valueOf(n);
        return s.equals(new StringBuilder(s).reverse().toString()) ? 1 : 0;
    }
}
