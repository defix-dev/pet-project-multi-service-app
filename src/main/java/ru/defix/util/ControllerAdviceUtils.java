package ru.defix.util;

import java.util.HashMap;
import java.util.Map;

public class ControllerAdviceUtils {
    @SafeVarargs
    public static Map<String, Object> prepareResponseData(Exception e, int statusCode, Map.Entry<String, Object>... additional) {
        Map<String, Object> responseData = new HashMap<>(Map.of(
                "error", e.getMessage(),
                "status", statusCode
        ));

        for (Map.Entry<String, Object> entry : additional) {
            responseData.put(entry.getKey(), entry.getValue());
        }

        return responseData;
    }
}
