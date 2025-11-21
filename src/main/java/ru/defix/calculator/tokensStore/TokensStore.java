package ru.defix.calculator.tokensStore;

import ru.defix.calculator.function.Function;

import java.util.Map;

public interface TokensStore {
    Map<String, OperatorTokenDetails> getOperators();
    Map<String, Function> getFunctions();
}
