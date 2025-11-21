package ru.defix.chat.service;

import ru.defix.chat.exception.NoMessagesException;
import ru.defix.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.defix.database.postgresql.entity.Message;
import ru.defix.database.postgresql.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final MessageRepository messageRepo;
    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepo, ChatService chatService,
                          UserService userService) {
        this.messageRepo = messageRepo;
        this.chatService = chatService;
        this.userService = userService;
    }

    public Message createMessageBetweenUsers(int senderId, int destinationId, String message) {
        return createMessageByChatId(
                chatService.getChatBetweenUsers(senderId, destinationId).getId(), senderId, message
        );
    }

    public Message createMessageByChatId(int chatId, int senderId, String message) {
        Message msg = new Message();
        msg.setChat(chatService.getChatById(chatId));
        msg.setMessage(message);
        msg.setUser(userService.findById(senderId));

        Message saved = messageRepo.save(msg);
        logger.info("Message has been created with id: {}", saved.getId());
        return saved;
    }

    public Message getLastMessageByChatId(int chatId) {
        Optional<Message> message = Optional.ofNullable(messageRepo.getLastMessageByChatId(chatId));
        if(message.isEmpty()) throw new NoMessagesException();
        logger.debug("Message received: {}", message.get().getMessage());
        return message.get();
    }

    public List<Message> getSortedMessagesByChatId(int chatId) {
        List<Message> messages = messageRepo.getSortedMessagesByChatId(chatId);
        if(messages.isEmpty()) throw new NoMessagesException();
        logger.debug("Received messages count: {}", messages.size());
        return messages;
    }
}
