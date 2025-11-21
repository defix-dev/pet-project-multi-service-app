package ru.defix.chat.service.creator;

import ru.defix.chat.api.dto.response.ChatKeysCreationDetails;
import ru.defix.chat.service.EncryptionService;
import ru.defix.chat.util.ChatCryptUtils;

public class EncryptedChatKeysCreator implements Creator<ChatKeysCreationDetails> {
    private final EncryptionService encryptionService;

    public EncryptedChatKeysCreator(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public void create(ChatKeysCreationDetails details) {
        encryptionService.createKeys(
                details.getUserId(),
                ChatCryptUtils.encrypt(details.getPrivateKey()),
                ChatCryptUtils.encrypt(details.getPublicKey())
        );
    }
}
