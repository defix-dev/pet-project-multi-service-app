package ru.defix.calculator;

import ru.defix.calculator.exception.CalculatorNotFoundException;
import ru.defix.calculator.interpretator.TokensTreeBuilder;
import ru.defix.calculator.interpretator.TokensTreeCalculator;
import ru.defix.calculator.interpretator.tokenizer.ExpressionTokenizer;
import ru.defix.calculator.api.dto.response.TokenDTO;
import ru.defix.calculator.tokensStore.GlobalTokensStore;
import ru.defix.calculator.tokensStore.TokensStore;

import java.util.ArrayList;
import java.util.List;

public class CalculatorUtils {
    public static double calculateExpression(String expression, TokensStore store) {
        return new TokensTreeCalculator(store).calculateExpression(
                new TokensTreeBuilder(store).build(
                        new ExpressionTokenizer(store).tokenizeExpression(expression)
                )
        );
    }

    public static double calculateExpression(String expression, String calcType) {
        TokensStore typedStore = getTokensStoreFromString(calcType);
        return new TokensTreeCalculator(typedStore).calculateExpression(
                new TokensTreeBuilder(typedStore).build(
                        new ExpressionTokenizer(typedStore).tokenizeExpression(expression)
                )
        );
    }

    public static List<TokenDTO> prepareTokensStoreToDTO(String calcType) {
        TokensStore typedStore = getTokensStoreFromString(calcType);
        List<TokenDTO> tokens = new ArrayList<>(typedStore.getFunctions().keySet().stream().map(
                v -> new TokenDTO("function", v)
        ).toList());
        tokens.addAll(
                typedStore.getOperators().keySet().stream().map(
                        v -> new TokenDTO("operator", v)
                ).toList()
        );
        return tokens;
    }

    private static TokensStore getTokensStoreFromString(String calcType) {
        try {
            return GlobalTokensStore.tokens.get(CalculatorType.valueOf(calcType.toUpperCase()));
        } catch (Exception e) {
            throw new CalculatorNotFoundException();
        }
    }
}
