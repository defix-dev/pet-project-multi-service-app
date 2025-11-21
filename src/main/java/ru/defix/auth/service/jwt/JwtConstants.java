package ru.defix.auth.service.jwt;

import io.jsonwebtoken.SignatureAlgorithm;

public class JwtConstants {
    public static final String JWT_SECRET_KEY_ENV = "JWT_SECRET_KEY";
    public static final SignatureAlgorithm CRYPT_ALG = SignatureAlgorithm.HS256;
    public static final int ACCESS_TOKEN_HOURS_EXP = 24;
}
