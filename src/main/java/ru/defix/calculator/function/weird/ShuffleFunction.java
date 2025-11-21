package ru.defix.calculator.function.weird;

import ru.defix.calculator.function.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShuffleFunction implements Function {
    public double calculate(double... args) {
        List<Character> digits = new ArrayList<>();
        for (char c : String.valueOf((int) args[0]).toCharArray()) {
            digits.add(c);
        }
        Collections.shuffle(digits);
        return Double.parseDouble(digits.stream().map(String::valueOf).collect(Collectors.joining()));
    }
}
