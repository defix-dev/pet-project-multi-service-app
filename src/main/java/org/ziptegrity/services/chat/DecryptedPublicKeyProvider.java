package org.ziptegrity.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.ziptegrity.services.chat.abstractions.Provider;

import java.util.Base64;

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
