package ru.defix.chat.service.creator;

import ru.defix.chat.api.dto.request.MessageCreationDetails;
import ru.defix.chat.service.MessageService;
import ru.defix.chat.util.ChatCryptUtils;
import ru.defix.database.postgresql.entity.Message;

public class EncryptedMessageCreator implements ReturnedCreator<Message, MessageCreationDetails> {
    private final MessageService messageService;

    public EncryptedMessageCreator(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public Message create(MessageCreationDetails details) {
        return messageService.createMessageBetweenUsers(
                details.getSenderId(), details.getDestinationId(), ChatCryptUtils.encrypt(
                        details.getMessage())
        );
    }
}
