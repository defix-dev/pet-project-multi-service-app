package ru.defix.chat.service.provider;

import ru.defix.chat.service.EncryptionService;
import ru.defix.chat.util.ChatCryptUtils;

public class DecryptedPrivateKeyProvider implements Provider<String, Integer> {
    private final EncryptionService encryptionService;

    public DecryptedPrivateKeyProvider(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public String provide(Integer userId) {
        return ChatCryptUtils.decrypt(encryptionService.getPrivateKey(userId));
    }
}
