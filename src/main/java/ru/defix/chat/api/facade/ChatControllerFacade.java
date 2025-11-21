package ru.defix.chat.api.facade;

import lombok.Getter;
import ru.defix.chat.service.provider.Provider;
import ru.defix.chat.api.dto.response.ChatApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class ChatControllerFacade {
    private final Provider<List<ChatApiDTO>, Integer> chatProvider;

    @Autowired
    public ChatControllerFacade(@Qualifier("chatProvider") Provider<List<ChatApiDTO>, Integer> chatProvider) {
        this.chatProvider = chatProvider;
    }
}
