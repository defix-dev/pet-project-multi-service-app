package ru.defix.chat.service;

import ru.defix.chat.exception.ChatKeysAlreadyCreatedException;
import ru.defix.chat.exception.ChatKeysNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.defix.database.postgresql.entity.ChatKeys;
import ru.defix.database.postgresql.repository.ChatKeysRepository;

@Service
public class EncryptionService {
    private final ChatKeysRepository chatKeysRepo;

    @Autowired
    public EncryptionService(ChatKeysRepository chatKeysRepo) {
        this.chatKeysRepo = chatKeysRepo;
    }

    public String getPublicKey(int userId) {
        return chatKeysRepo.findById(userId).orElseThrow(ChatKeysNotFoundException::new)
                .getPublicKey();
    }

    public String getPrivateKey(int userId) {
        return chatKeysRepo.findById(userId).orElseThrow(ChatKeysNotFoundException::new)
                .getPrivateKey();
    }

    public void createKeys(int userId, String privateKey, String publicKey) {
        if(chatKeysRepo.existsById(userId)) throw new ChatKeysAlreadyCreatedException();
        ChatKeys keys = new ChatKeys();
        keys.setPrivateKey(privateKey);
        keys.setPublicKey(publicKey);
        keys.setUserId(userId);

        chatKeysRepo.save(keys);
    }
}
