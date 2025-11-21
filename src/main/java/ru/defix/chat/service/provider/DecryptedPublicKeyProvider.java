package ru.defix.chat.service.provider;

import ru.defix.chat.service.EncryptionService;
import ru.defix.chat.util.ChatCryptUtils;

public class DecryptedPublicKeyProvider implements Provider<String, Integer> {
    private final EncryptionService encryptionService;

    public DecryptedPublicKeyProvider(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public String provide(Integer userId) {
        return ChatCryptUtils.decrypt(encryptionService.getPublicKey(userId));
    }
}
