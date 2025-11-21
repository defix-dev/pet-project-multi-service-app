package ru.defix.calculator.interpretator.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MappedTokenImpl implements MappedToken {
    private final TokenType tokenType;
    private final String value;

    @Override
    public TokenType identify() {
        return tokenType;
    }
}
