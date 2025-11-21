package ru.defix.chat.api.facade;

import lombok.Getter;
import ru.defix.chat.service.provider.BiProvider;
import ru.defix.chat.api.dto.response.MessageApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class MessageControllerFacade {
    private final BiProvider<List<MessageApiDTO>, Integer> messageProvider;

    @Autowired
    public MessageControllerFacade(@Qualifier("messageProvider") BiProvider<List<MessageApiDTO>, Integer> messageProvider) {
        this.messageProvider = messageProvider;
    }
}
