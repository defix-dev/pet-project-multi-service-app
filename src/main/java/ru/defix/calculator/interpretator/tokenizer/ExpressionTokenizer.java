package ru.defix.calculator.interpretator.tokenizer;

import lombok.AllArgsConstructor;
import ru.defix.calculator.interpretator.token.TokenType;
import ru.defix.calculator.tokensStore.TokensStore;
import ru.defix.calculator.exception.FunctionNotFoundException;
import ru.defix.calculator.interpretator.token.RawToken;

import java.util.*;

@AllArgsConstructor
public class ExpressionTokenizer {
    private final TokensStore store;

    public List<RawToken> tokenizeExpression(String expression) {
        ExpressionValidator.validateRawExpression(expression);
        expression = prepareRawExpression(expression);

        List<RawToken> tokens = new ArrayList<>();
        Deque<Character> expQueue = new ArrayDeque<>(expression.chars().mapToObj(c -> (char) c).toList());

        StringBuilder tokenBuilder = new StringBuilder();
        TokenType expectedTokenType = TokenType.NONE;

        while (true) {
            if(tokenBuilder.toString().equals("(") && expectedTokenType == TokenType.FUNCTION) expectedTokenType = TokenType.EXPRESSION;
            if (expectedTokenType == TokenType.EXPRESSION || expectedTokenType == TokenType.FUNCTION) {
                int depth = 1;
                while (depth != 0) {
                    String s = String.valueOf(expQueue.poll());
                    if (s.equals(")")) depth--;
                    if (s.equals("(")) depth++;
                    tokenBuilder.append(s);
                }
            }

            Character current = expQueue.poll();
            if (current == null) {
                tokens.add(createRawToken(expectedTokenType, tokenBuilder.toString()));
                break;
            }
            TokenType innerExpectedType = getExpectedTokenType(current, expectedTokenType);

            final boolean isException = !(expectedTokenType == TokenType.OPERATOR && innerExpectedType == TokenType.FUNCTION)
                    && expectedTokenType != TokenType.NONE;
            final boolean isOperatorExist = expectedTokenType == TokenType.OPERATOR && store.getOperators().containsKey(tokenBuilder.toString());
            if ((expectedTokenType != innerExpectedType && isException) || isOperatorExist) {
                tokens.add(createRawToken(expectedTokenType, tokenBuilder.toString()));
                tokenBuilder.setLength(0);
            }

            expectedTokenType = innerExpectedType;
            tokenBuilder.append(current);
        }
        ExpressionValidator.validateTokenizedExpression(tokens);
        return tokens;
    }

    private RawToken createRawToken(TokenType type, String value) {
        if(type == TokenType.FUNCTION && !store.getFunctions().containsKey(value.substring(0, value.indexOf('(')))) throw new FunctionNotFoundException();
        return new RawToken(type, prepareTokenValue(type, value));
    }

    private static String prepareRawExpression(String expression) {
        expression = expression.replace(" ", "");
        return expression.startsWith("-") ? String.format("0%s", expression) : expression;
    }

    private static String prepareTokenValue(TokenType expectedType, String interimTV) {
        return expectedType != TokenType.EXPRESSION ? interimTV : deleteBrackets(interimTV);
    }

    private static String deleteBrackets(String expression) {
        return expression.substring(1, expression.length()-1);
    }

    private static TokenType getExpectedTokenType(char current, TokenType defaultType) {
        if (RawTokenResolver.isOperator(current)) return TokenType.OPERATOR;
        else if (RawTokenResolver.isOperand(current)) return TokenType.OPERAND;
        else if (RawTokenResolver.isFunction(current)) return TokenType.FUNCTION;
        return defaultType;
    }
}