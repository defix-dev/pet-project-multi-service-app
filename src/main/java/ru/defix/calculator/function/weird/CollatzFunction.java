
package ru.defix.calculator.function.weird;

import ru.defix.calculator.function.Function;

public class CollatzFunction implements Function {
    public double calculate(double... args) {
        int n = (int) args[0];
        int count = 0;
        while (n != 1) {
            n = (n % 2 == 0) ? n / 2 : 3 * n + 1;
            count++;
        }
        return count;
    }
}
