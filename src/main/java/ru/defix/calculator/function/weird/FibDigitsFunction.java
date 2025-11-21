package ru.defix.calculator.function.weird;

import ru.defix.calculator.function.Function;

import java.util.Arrays;

public class FibDigitsFunction implements Function {
    public double calculate(double... args) {
        String n = String.valueOf((int) args[0]);
        return Arrays.stream(n.split(""))
                .mapToInt(Integer::parseInt)
                .map(this::fibonacci)
                .sum();
    }

    private int fibonacci(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1, sum = 0;
        for (int i = 2; i <= n; i++) {
            sum = a + b;
            a = b;
            b = sum;
        }
        return sum;
    }
}
