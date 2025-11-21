package ru.defix.calculator.interpretator.tokenizer;

import lombok.AllArgsConstructor;
import ru.defix.calculator.tokensStore.TokensStore;
import ru.defix.calculator.interpretator.token.RawToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FunctionTokenizer {
    private final TokensStore store;

    public List<List<RawToken>> tokenizeFunction(String rawFunc) {
        int start = rawFunc.indexOf('(') + 1;
        int end = rawFunc.lastIndexOf(')');
        if (start <= 0 || end <= start) return new LinkedList<>();

        String content = rawFunc.substring(start, end);
        List<String> paramList = parseFunctionParams(content);

        return paramList.stream()
                .map(v -> new ExpressionTokenizer(store).tokenizeExpression(v))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static List<String> parseFunctionParams(String content) {
        List<String> paramList = new ArrayList<>();
        StringBuilder currentParam = new StringBuilder();

        int depth = 0;
        for (char c : content.toCharArray()) {
            if (c == '(') depth++;
            if (c == ')') depth--;

            if (c == ',' && depth == 0) {
                paramList.add(currentParam.toString().trim());
                currentParam.setLength(0);
            } else {
                currentParam.append(c);
            }
        }
        if (!currentParam.isEmpty()) paramList.add(currentParam.toString().trim());
        return paramList;
    }
}
