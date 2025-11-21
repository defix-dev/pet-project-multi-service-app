package ru.defix.tests.service;

import ru.defix.calculator.CalculatorUtils;
import ru.defix.calculator.interpretator.tokenizer.ExpressionTokenizer;
import ru.defix.calculator.interpretator.tokenizer.FunctionTokenizer;
import ru.defix.calculator.interpretator.TokensTreeBuilder;
import ru.defix.calculator.interpretator.token.MappedToken;
import ru.defix.calculator.interpretator.token.MappedTokenImpl;
import ru.defix.calculator.interpretator.token.RawToken;
import ru.defix.calculator.interpretator.token.TokenType;
import ru.defix.calculator.interpretator.token.MappedExpressionToken;
import ru.defix.calculator.interpretator.token.MappedFunctionToken;
import ru.defix.calculator.tokensStore.store.ProgrammerTokensStore;
import ru.defix.calculator.tokensStore.store.SimpleTokensStore;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ServiceTests
public class CalculatorServiceTests {
    public record ExpressionTestObj(String expression, LinkedList<RawToken> tokens) {
    }

    public record FunctionTestObj(String function, LinkedList<LinkedList<RawToken>> tokenizedParams) {
    }

    public record MappedExpressionTestObj(String expression, LinkedList<MappedToken> tokens) {
    }

    public record ResultTestObj(String expression, double result) {
    }

    @ParameterizedTest
    @MethodSource("provideExpressionObjs")
    public void expressionTokenizerTest(ExpressionTestObj testObj) {
        List<RawToken> tokens = new ExpressionTokenizer(new SimpleTokensStore()).tokenizeExpression(testObj.expression);

        assertFalse(tokens.isEmpty());
        assertEquals(testObj.tokens.size(), tokens.size());

        for (int i = 0; i < tokens.size(); i++) {
            RawToken expectedToken = testObj.tokens.get(i);
            RawToken actualToken = tokens.get(i);
            assertEquals(expectedToken.getValue(), actualToken.getValue());
            assertEquals(expectedToken.getType(), actualToken.getType());
        }
    }

    @ParameterizedTest
    @MethodSource("provideTokenizedParamObjs")
    public void functionTokenizerTest(FunctionTestObj testObj) {
        List<List<RawToken>> tokenizedParams = new FunctionTokenizer(new SimpleTokensStore()).tokenizeFunction(testObj.function);

        assertFalse(tokenizedParams.isEmpty());
        assertEquals(testObj.tokenizedParams.size(), tokenizedParams.size());

        for (int i = 0; i < tokenizedParams.size(); i++) {
            for (int j = 0; j < tokenizedParams.get(i).size(); j++) {
                RawToken expectedToken = testObj.tokenizedParams.get(i).get(j);
                RawToken actualToken = tokenizedParams.get(i).get(j);
                assertEquals(expectedToken.getType(), actualToken.getType());
                assertEquals(expectedToken.getValue(), actualToken.getValue());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideMappedExpressionObjs")
    public void tokenTreeBuilderTest(MappedExpressionTestObj testObj) {
        List<MappedToken> mappedTokens = new TokensTreeBuilder(new SimpleTokensStore())
                .build(new ExpressionTokenizer(new SimpleTokensStore()).tokenizeExpression(
                testObj.expression
        ));

        assertFalse(mappedTokens.isEmpty());
        assertEquals(testObj.tokens.size(), mappedTokens.size());

        for (int i = 0; i < mappedTokens.size(); i++) {
            MappedToken expectedToken = testObj.tokens.get(i);
            MappedToken actualToken = mappedTokens.get(i);
            assertEquals(expectedToken.identify(), actualToken.identify());
        }
    }

    @ParameterizedTest
    @MethodSource("provideSimpleObjs")
    public void calculatorSimpleTest(ResultTestObj testObj) {
        assertEquals(testObj.result, CalculatorUtils.calculateExpression(testObj.expression, new SimpleTokensStore()));
    }

    @ParameterizedTest
    @MethodSource("provideProgrammerObjs")
    public void calculatorProgrammerTest(ResultTestObj testObj) {
        assertEquals(testObj.result, CalculatorUtils.calculateExpression(testObj.expression, new ProgrammerTokensStore()));
    }

    private static Stream<ResultTestObj> provideProgrammerObjs() {
        return Stream.of(
                new ResultTestObj("5 & 3 | 2 ^ 1 << 1", 1)
        );
    }

    private static Stream<ResultTestObj> provideSimpleObjs() {
        return Stream.of(
                new ResultTestObj("1+1-4", -2),
                new ResultTestObj("1+1-44.5", -42.5),
                new ResultTestObj("1+sqrt(2+2)-44.5", -41.5),
                new ResultTestObj("1+sqrt(2+2)-44.5*(3+3*3)", -531),
                new ResultTestObj("1+sqrt(2+2)-44.5*(3+pow(2,sqrt(4))*3)", -664.5),
                new ResultTestObj("-1", -1),
                new ResultTestObj("1", 1),
                new ResultTestObj("sqrt(pow(sqrt(pow(sqrt(pow(sqrt(256), 4)), 3)), 2))", 4096),
                new ResultTestObj("sin(sqrt(0))", 0),
                new ResultTestObj("cos(sqrt(0))", 1),
                new ResultTestObj("2^4", 16),
                new ResultTestObj("2^(1-1)", 1),
                new ResultTestObj("44,5", 44.5)
        );
    }

    private static Stream<FunctionTestObj> provideTokenizedParamObjs() {
        return Stream.of(
                new FunctionTestObj("sqrt(1+1)", new LinkedList<>(List.of(
                        new LinkedList<>(List.of(
                                new RawToken(TokenType.OPERAND, "1"),
                                new RawToken(TokenType.OPERATOR, "+"),
                                new RawToken(TokenType.OPERAND, "1")
                        ))
                )))
        );
    }

    private static Stream<MappedExpressionTestObj> provideMappedExpressionObjs() {
        return Stream.of(
                new MappedExpressionTestObj("1+1-4", new LinkedList<>(List.of(
                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                        new MappedTokenImpl(TokenType.OPERATOR, "+"),
                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                        new MappedTokenImpl(TokenType.OPERATOR, "-"),
                        new MappedTokenImpl(TokenType.OPERAND, "4")
                ))),
                new MappedExpressionTestObj("1+1-44,5", new LinkedList<>(List.of(
                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                        new MappedTokenImpl(TokenType.OPERATOR, "+"),
                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                        new MappedTokenImpl(TokenType.OPERATOR, "-"),
                        new MappedTokenImpl(TokenType.OPERAND, "44,5")
                ))),
                new MappedExpressionTestObj("1+sqrt(1+1)-44,5", new LinkedList<>(List.of(
                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                        new MappedTokenImpl(TokenType.OPERATOR, "+"),
                        new MappedFunctionToken("sqrt", new LinkedList<>(List.of(
                                new LinkedList<>(List.of(
                                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                                        new MappedTokenImpl(TokenType.OPERATOR, "+"),
                                        new MappedTokenImpl(TokenType.OPERAND, "1"))
                                )
                        ))),
                        new MappedTokenImpl(TokenType.OPERATOR, "-"),
                        new MappedTokenImpl(TokenType.OPERAND, "44,5")
                ))),
                new MappedExpressionTestObj("1+sqrt(1+1)-44,5*(3+3*3)", new LinkedList<>(List.of(
                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                        new MappedTokenImpl(TokenType.OPERATOR, "+"),
                        new MappedFunctionToken("sqrt", new LinkedList<>(List.of(
                                new LinkedList<>(List.of(
                                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                                        new MappedTokenImpl(TokenType.OPERATOR, "+"),
                                        new MappedTokenImpl(TokenType.OPERAND, "1"))
                                )
                        ))),
                        new MappedTokenImpl(TokenType.OPERATOR, "-"),
                        new MappedTokenImpl(TokenType.OPERAND, "44,5"),
                        new MappedTokenImpl(TokenType.OPERATOR, "*"),
                        new MappedExpressionToken(new LinkedList<>(List.of(
                                new MappedTokenImpl(TokenType.OPERAND, "3"),
                                new MappedTokenImpl(TokenType.OPERATOR, "+"),
                                new MappedTokenImpl(TokenType.OPERAND, "3"),
                                new MappedTokenImpl(TokenType.OPERATOR, "*"),
                                new MappedTokenImpl(TokenType.OPERAND, "3")
                        )))
                ))),
                new MappedExpressionTestObj("1+sqrt(1, 2)-(3*3-3)", new LinkedList<>(List.of(
                        new MappedTokenImpl(TokenType.OPERAND, "1"),
                        new MappedTokenImpl(TokenType.OPERATOR, "+"),
                        new MappedFunctionToken("sqrt", new LinkedList<>(List.of(
                                new LinkedList<>(List.of(
                                        new MappedTokenImpl(TokenType.OPERAND, "1")
                                )),
                                new LinkedList<>(List.of(
                                        new MappedTokenImpl(TokenType.OPERAND, "2")
                                ))
                        ))),
                        new MappedTokenImpl(TokenType.OPERATOR, "-"),
                        new MappedExpressionToken(new LinkedList<>(List.of(
                                new MappedTokenImpl(TokenType.OPERAND, "3"),
                                new MappedTokenImpl(TokenType.OPERATOR, "*"),
                                new MappedTokenImpl(TokenType.OPERAND, "3"),
                                new MappedTokenImpl(TokenType.OPERATOR, "-"),
                                new MappedTokenImpl(TokenType.OPERAND, "3")
                        )))
                )))
        );
    }

    private static Stream<ExpressionTestObj> provideExpressionObjs() {
        return Stream.of(
                new ExpressionTestObj("1+1-4", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "4")
                ))),
                new ExpressionTestObj("1+1-44,5", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44,5")
                ))),
                new ExpressionTestObj("1+1-44.5", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44.5")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.FUNCTION, "sqrt(1)"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44,5")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5*(3+3*3)", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.FUNCTION, "sqrt(1)"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44,5"),
                        new RawToken(TokenType.OPERATOR, "*"),
                        new RawToken(TokenType.EXPRESSION, "3+3*3")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5*(3+3*3-(2*2+(1+1)))-pow((1+1), 2)", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.FUNCTION, "sqrt(1)"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44,5"),
                        new RawToken(TokenType.OPERATOR, "*"),
                        new RawToken(TokenType.EXPRESSION, "3+3*3-(2*2+(1+1))"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.FUNCTION, "pow((1+1),2)")
                )))
        );
    }
}
