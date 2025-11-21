package ru.defix.calculator.interpretator.tokenizer;

import ru.defix.calculator.exception.BadExpressionFormatException;
import ru.defix.calculator.exception.EmptyExpressionException;
import ru.defix.calculator.interpretator.token.RawToken;

import java.util.List;

public class ExpressionValidator {
    public static void validateRawExpression(String expression) {
        if(expression == null || expression.isEmpty()) throw new EmptyExpressionException();
        if(charCount(expression, '(') != charCount(expression, ')')) throw new BadExpressionFormatException();
    }

    public static void validateTokenizedExpression(List<RawToken> tokens) {
        if(tokens.size() % 2 == 0) throw new BadExpressionFormatException();
    }

    private static int charCount(String expression, Character target) {
        return (int) expression.chars().filter(c -> c == target).count();
    }
}
