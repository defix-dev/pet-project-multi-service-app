package ru.defix.chat.api.facade;

import lombok.Getter;
import ru.defix.chat.service.creator.Creator;
import ru.defix.chat.service.provider.Provider;
import ru.defix.chat.api.dto.response.ChatKeysCreationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EncryptionControllerFacade {
    private final Provider<String, Integer> privateKeyProvider;
    private final Provider<String, Integer> publicKeyProvider;
    private final Creator<ChatKeysCreationDetails> keysCreator;

    @Autowired
    public EncryptionControllerFacade(@Qualifier("privateKeyProvider") Provider<String, Integer> privateKeyProvider,
                                  @Qualifier("publicKeyProvider") Provider<String, Integer> publicKeyProvider,
                                  @Qualifier("keysCreator") Creator<ChatKeysCreationDetails> keysCreator) {
        this.privateKeyProvider = privateKeyProvider;
        this.publicKeyProvider = publicKeyProvider;
        this.keysCreator = keysCreator;
    }
}
