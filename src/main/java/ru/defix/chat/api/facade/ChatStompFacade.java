package ru.defix.chat.api.facade;

import ru.defix.chat.service.ChatService;
import ru.defix.chat.service.creator.ReturnedCreator;
import ru.defix.chat.api.dto.request.MessageCreationDetails;
import ru.defix.chat.api.dto.response.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.defix.database.postgresql.entity.Message;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class ChatStompFacade {
    private static final Logger logger = LoggerFactory.getLogger(ChatStompFacade.class);
    private final SimpMessagingTemplate message;
    private final ChatService chatService;
    private final ReturnedCreator<Message, MessageCreationDetails> messageCreator;

    @Autowired
    public ChatStompFacade(SimpMessagingTemplate message,
                           ChatService chatService,
                           @Qualifier("messageCreator") ReturnedCreator<Message, MessageCreationDetails> messageCreator) {
        this.message = message;
        this.chatService = chatService;
        this.messageCreator = messageCreator;
    }

    public void sendMessageToChat(int senderId, int destinationId, String message) {
        if(!chatService.hasChat(senderId, destinationId)) {
            chatService.createChat(senderId, destinationId);
            logger.info("Created chat between {} {}", senderId, destinationId);
        }

        LocalDateTime createdAt = messageCreator.create(
                new MessageCreationDetails(senderId, destinationId, message)
        ).getCreatedAt();

        MessageDTO dto = new MessageDTO(
                senderId, message, createdAt
        );

        this.message.convertAndSend(MessageFormat.format("/chat/listen/{0}", destinationId), dto,
                Collections.singletonMap("content-type", "application/json"));
        this.message.convertAndSend(MessageFormat.format("/chat/listen/{0}", senderId), dto,
                Collections.singletonMap("content-type", "application/json"));
    }
}
