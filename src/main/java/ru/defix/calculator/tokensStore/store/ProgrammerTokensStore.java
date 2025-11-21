package ru.defix.calculator.tokensStore.store;

import ru.defix.calculator.function.Function;
import ru.defix.calculator.operator.programmer.*;
import ru.defix.calculator.tokensStore.TokensStore;
import ru.defix.calculator.function.programmer.AndFunction;
import ru.defix.calculator.function.programmer.NotFunction;
import ru.defix.calculator.function.programmer.OrFunction;
import ru.defix.calculator.function.programmer.XorFunction;
import ru.defix.calculator.tokensStore.OperatorTokenDetails;
import java.util.Map;

public class ProgrammerTokensStore implements TokensStore {

    @Override
    public Map<String, OperatorTokenDetails> getOperators() {
        return Map.ofEntries(
                Map.entry("&", new OperatorTokenDetails(new BitwiseAndOperator(), 3)),
                Map.entry("|", new OperatorTokenDetails(new BitwiseOrOperator(), 2)),
                Map.entry("^", new OperatorTokenDetails(new BitwiseXorOperator(), 2)),
                Map.entry("<<", new OperatorTokenDetails(new LeftShiftOperator(), 4)),
                Map.entry(">>", new OperatorTokenDetails(new RightShiftOperator(), 4))
        );
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.ofEntries(
                Map.entry("not", new NotFunction()),
                Map.entry("and", new AndFunction()),
                Map.entry("or", new OrFunction()),
                Map.entry("xor", new XorFunction())
        );
    }
}
