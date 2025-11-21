package ru.defix.auth.service.jwt;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtValidator {
    private static final Logger logger = LoggerFactory.getLogger(JwtValidator.class);

    public static boolean validate(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(new SecretKeySpec(JwtUtils.getEnvSecretKey().getBytes(), JwtConstants.CRYPT_ALG.getJcaName()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            logger.error("Failed to validate token: {}", e.getMessage());
            return false; }
    }
}
