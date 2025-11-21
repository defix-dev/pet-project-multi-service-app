package ru.defix.calculator.interpretator.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MappedFunctionToken implements MappedToken {
    private String keyword;
    private List<List<MappedToken>> tokenizedParams;

    @Override
    public TokenType identify() {
        return TokenType.FUNCTION;
    }
}
