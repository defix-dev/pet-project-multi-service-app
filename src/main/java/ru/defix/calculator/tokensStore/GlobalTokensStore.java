package ru.defix.calculator.tokensStore;

import ru.defix.calculator.CalculatorType;
import ru.defix.calculator.tokensStore.store.CombinedTokensStore;
import ru.defix.calculator.tokensStore.store.ProgrammerTokensStore;
import ru.defix.calculator.tokensStore.store.SimpleTokensStore;

import java.util.Map;

public class GlobalTokensStore {
    public static final Map<CalculatorType, TokensStore> tokens = Map.ofEntries(
            Map.entry(CalculatorType.SIMPLE, new SimpleTokensStore()),
            Map.entry(CalculatorType.PROGRAMMER, new ProgrammerTokensStore()),
            Map.entry(CalculatorType.COMBINED, new CombinedTokensStore())
    );
}
