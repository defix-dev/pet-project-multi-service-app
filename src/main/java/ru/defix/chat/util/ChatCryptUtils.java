package ru.defix.chat.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.text.MessageFormat;
import java.util.Base64;

public class ChatCryptUtils {
    public static String encrypt(String value) {
        try {
            SecretKeySpec secretKey = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(ChatConstants.MESSAGE_CRYPT_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessage = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format("Failed to encrypt: {0}", e.getMessage()));
        }
    }

    public static String decrypt(String value) {
        try {
            SecretKeySpec secretKey = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(ChatConstants.MESSAGE_CRYPT_ALG);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedMessage = cipher.doFinal(Base64.getDecoder().decode(value));
            return new String(decryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format("Failed to encrypt: {0}", e.getMessage()));
        }
    }

    public static SecretKeySpec getSecretKeySpec() {
        String key = System.getenv(ChatConstants.MESSAGE_SECRET_KEY);
        if(key == null) throw new IllegalArgumentException("Message secret key not found.");
        return new SecretKeySpec(Base64.getDecoder().decode(key), ChatConstants.MESSAGE_CRYPT_ALG);
    }
}
