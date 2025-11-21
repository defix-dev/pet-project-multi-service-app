package ru.defix.calculator.interpretator.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MappedExpressionToken implements MappedToken {
    private List<MappedToken> tokens;

    @Override
    public TokenType identify() {
        return TokenType.EXPRESSION;
    }
}
