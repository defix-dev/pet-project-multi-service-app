package ru.defix.calculator.tokensStore.store;

import ru.defix.calculator.function.simple.CosinusFunction;
import ru.defix.calculator.function.simple.PowerFunction;
import ru.defix.calculator.function.simple.SinusFunction;
import ru.defix.calculator.function.simple.SqrtFunction;
import ru.defix.calculator.operator.simple.*;
import ru.defix.calculator.tokensStore.OperatorTokenDetails;
import ru.defix.calculator.function.Function;
import ru.defix.calculator.tokensStore.TokensStore;

import java.util.Map;

public class SimpleTokensStore implements TokensStore {

    @Override
    public Map<String, OperatorTokenDetails> getOperators() {
        return Map.ofEntries(
                Map.entry("+", new OperatorTokenDetails(new PlusOperator(), 1)),
                Map.entry("-", new OperatorTokenDetails(new MinusOperator(), 1)),
                Map.entry("*", new OperatorTokenDetails(new MultiplyOperator(), 2)),
                Map.entry("/", new OperatorTokenDetails(new DivideOperator(), 2)),
                Map.entry("^", new OperatorTokenDetails(new PowerOperator(), 2))
        );
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.ofEntries(
                Map.entry("sqrt", new SqrtFunction()),
                Map.entry("pow", new PowerFunction()),
                Map.entry("sin", new SinusFunction()),
                Map.entry("cos", new CosinusFunction())
        );
    }
}
