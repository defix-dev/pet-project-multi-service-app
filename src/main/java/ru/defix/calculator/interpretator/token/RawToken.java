package ru.defix.calculator.interpretator.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RawToken {
    private TokenType type;
    private String value;
}
