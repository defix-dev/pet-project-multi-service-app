package ru.defix.chat.service.provider;

import ru.defix.chat.service.ChatService;
import ru.defix.chat.service.MessageService;
import ru.defix.chat.util.ChatCryptUtils;
import ru.defix.database.postgresql.entity.Message;
import ru.defix.chat.api.dto.response.MessageApiDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DecryptedMessageProvider implements BiProvider<List<MessageApiDTO>, Integer> {
    private final ChatService chatService;
    private final MessageService messageService;

    public DecryptedMessageProvider(ChatService chatService,
                                    MessageService messageService) {
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @Override
    public List<MessageApiDTO> provide(Integer aId, Integer bId) {
        List<Message> messages = messageService.getSortedMessagesByChatId(chatService.getChatBetweenUsers(aId, bId).getId());
        return messages.stream().map(msg -> new MessageApiDTO(
                ChatCryptUtils.decrypt(msg.getMessage()),
                msg.getCreatedAt(),
                msg.getUser().getId())).collect(Collectors.toList());
    }
}
