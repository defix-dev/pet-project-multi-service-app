package ru.defix.calculator.interpretator;

import lombok.AllArgsConstructor;
import ru.defix.calculator.tokensStore.TokensStore;
import ru.defix.calculator.interpretator.tokenizer.ExpressionTokenizer;
import ru.defix.calculator.interpretator.tokenizer.FunctionTokenizer;
import ru.defix.calculator.interpretator.token.RawToken;
import ru.defix.calculator.interpretator.token.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TokensTreeBuilder {
    private final TokensStore store;

    public List<MappedToken> build(List<RawToken> tokens) {
        List<MappedToken> result = new ArrayList<>();
        for (RawToken token : tokens) {
            switch (token.getType()) {
                case EXPRESSION -> result.add(new MappedExpressionToken(
                        build(
                                new ExpressionTokenizer(store).tokenizeExpression(token.getValue())
                        )
                ));
                case FUNCTION -> {
                    int index = token.getValue().indexOf('(');
                    String keyword = (index > 0) ? token.getValue().substring(0, index) : token.getValue();

                    List<List<MappedToken>> tokenizedParams = new ArrayList<>();
                    for (List<RawToken> paramExp : new FunctionTokenizer(store).tokenizeFunction(token.getValue())) {
                        tokenizedParams.add(build(paramExp));
                    }

                    result.add(new MappedFunctionToken(keyword, tokenizedParams));
                }
                default -> result.add(new MappedTokenImpl(token.getType(), token.getValue()));
            }
        }

        return result;
    }
}
