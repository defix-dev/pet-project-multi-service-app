package ru.defix.chat.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import ru.defix.chat.api.facade.ChatStompFacade;
import ru.defix.user.service.UserService;

import java.security.Principal;

/**
 * WebSocket (STOMP) controller responsible for handling real-time chat messages.
 * Maps WebSocket messages to server-side chat processing.
 */
@Controller
public class ChatStompController {

    private static final Logger logger = LoggerFactory.getLogger(ChatStompController.class);
    private final ChatStompFacade chatFacade;
    private final UserService userService;

    /**
     * Constructs a new ChatStompController.
     *
     * @param chatFacade  Facade for handling WebSocket chat messaging logic.
     * @param userService Service for retrieving user data.
     */
    @Autowired
    public ChatStompController(ChatStompFacade chatFacade,
                               UserService userService) {
        this.chatFacade = chatFacade;
        this.userService = userService;
    }

    /**
     * Handles incoming chat messages via WebSocket.
     * The message is routed to the specified chat ID.
     *
     * @param id        The target chat ID.
     * @param message   The message content.
     * @param principal The authenticated user sending the message.
     */
    @MessageMapping("/chat/{id}")
    public void sendMessage(@DestinationVariable("id") int id, String message, Principal principal) {
        logger.debug("Message to send: {}", message);
        chatFacade.sendMessageToChat(
                userService.findByUsername(principal.getName()).getId(),
                id,
                message
        );
    }
}
