package ru.defix.calculator.interpretator.tokenizer;

public class RawTokenResolver {
    public static boolean isOperator(char current) {
        return !Character.isDigit(current) && ",.()".indexOf(current) == -1;
    }

    public static boolean isOperand(char current) {
        return Character.isDigit(current) || current == ',' || current == '.';
    }

    public static boolean isFunction(char current) {
        return !Character.isDigit(current) && ",.".indexOf(current) == -1;
    }
}
