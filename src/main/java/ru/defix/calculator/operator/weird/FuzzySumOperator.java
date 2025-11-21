package ru.defix.calculator.operator.weird;

import ru.defix.calculator.operator.Operator;

public class FuzzySumOperator implements Operator {
    public double calculate(double a, double b) {
        String aStr = String.valueOf((int) a);
        String bStr = String.valueOf((int) b);

        int maxLen = Math.max(aStr.length(), bStr.length());
        aStr = String.format("%1$" + maxLen + "s", aStr).replace(' ', '0');
        bStr = String.format("%1$" + maxLen + "s", bStr).replace(' ', '0');

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < maxLen; i++) {
            int sum = Character.getNumericValue(aStr.charAt(i)) + Character.getNumericValue(bStr.charAt(i));
            result.append(sum);
        }
        return Double.parseDouble(result.toString());
    }
}
