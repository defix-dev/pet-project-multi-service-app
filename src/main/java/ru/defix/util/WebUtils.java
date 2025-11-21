package ru.defix.util;

import java.util.Base64;

public class WebUtils {
    public static String decodeFromBase64(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }
}
