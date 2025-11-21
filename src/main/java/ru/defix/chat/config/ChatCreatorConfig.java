package ru.defix.chat.config;

import ru.defix.chat.service.creator.EncryptedChatKeysCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.defix.chat.service.creator.EncryptedMessageCreator;
import ru.defix.chat.service.EncryptionService;
import ru.defix.chat.service.MessageService;

@Configuration
public class ChatCreatorConfig {
    private final EncryptionService encryptionService;
    private final MessageService messageService;

    @Autowired
    public ChatCreatorConfig(EncryptionService encryptionService, MessageService messageService) {
        this.encryptionService = encryptionService;
        this.messageService = messageService;
    }

    @Bean(name = "keysCreator")
    public EncryptedChatKeysCreator keysCreator() {
        return new EncryptedChatKeysCreator(encryptionService);
    }

    @Bean(name = "messageCreator")
    public EncryptedMessageCreator messageCreator() {
        return new EncryptedMessageCreator(messageService);
    }
}
