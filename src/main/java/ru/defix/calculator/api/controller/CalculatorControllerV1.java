package ru.defix.calculator.api.controller;

import ru.defix.calculator.CalculatorUtils;
import ru.defix.calculator.api.dto.request.ExpressionDTO;
import ru.defix.calculator.api.dto.response.TokenDTO;
import ru.defix.util.WebUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling calculator operations.
 * Provides endpoints for evaluating expressions and retrieving available tokens.
 */
@RestController
@RequestMapping("/api/v1/calculator")
public class CalculatorControllerV1 {

    /**
     * Calculates the result of a mathematical expression based on its type.
     * Requires the user to be authenticated.
     *
     * @param type       The type of the calculator (e.g. "simple", "programmer")
     * @param expression Encoded expression object containing a Base64-encoded math expression
     * @return ResponseEntity containing the result of the evaluated expression
     */
    @PostMapping("/{type}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Double> calculate(@PathVariable String type, @RequestBody ExpressionDTO expression) {
        return ResponseEntity.ok(CalculatorUtils.calculateExpression(
                WebUtils.decodeFromBase64(expression.getExpression()),
                type
        ));
    }

    /**
     * Retrieves a list of available tokens (operators, functions, etc.) for a specific calculator type.
     * Requires the user to be authenticated.
     *
     * @param type The type of the calculator
     * @return ResponseEntity containing a list of token DTOs
     */
    @GetMapping("/{type}/tokens")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TokenDTO>> getTokens(@PathVariable String type) {
        return ResponseEntity.ok(
                CalculatorUtils.prepareTokensStoreToDTO(type)
        );
    }
}