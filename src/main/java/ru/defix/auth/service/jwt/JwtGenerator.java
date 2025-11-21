package ru.defix.auth.service.jwt;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtGenerator {
    private static final Logger logger = LoggerFactory.getLogger(JwtGenerator.class);

    public static String generateAccessToken(String username) {
        try {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, JwtConstants.ACCESS_TOKEN_HOURS_EXP);
        return generateToken(Map.ofEntries(
                Map.entry("username", username)
        ), c.getTime()); } catch (Exception e) { logger.error("Failed to generate access token: {}", e.getMessage()); throw e; }
    }

    private static String generateToken(Map<String, Object> claims, Date exp) {
        return Jwts.builder().addClaims(claims)
                .setExpiration(exp)
                .signWith(new SecretKeySpec(JwtUtils.getEnvSecretKey().getBytes(), JwtConstants.CRYPT_ALG.getJcaName()))
                .compact();
    }
}
