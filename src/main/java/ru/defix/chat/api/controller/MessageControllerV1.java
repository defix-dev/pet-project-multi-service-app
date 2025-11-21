package ru.defix.chat.api.controller;

import ru.defix.chat.api.facade.MessageControllerFacade;
import ru.defix.chat.api.dto.response.MessageApiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.defix.user.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * REST controller for retrieving chat messages.
 * Provides access to messages exchanged with a specific user.
 */
@RestController
@RequestMapping("/api/v1/messages")
public class MessageControllerV1 {
    private static final Logger logger = LoggerFactory.getLogger(MessageControllerV1.class);
    private final MessageControllerFacade messageFacade;
    private final UserService userService;

    /**
     * Constructs a MessageControllerV1 with required dependencies.
     *
     * @param messageFacade Facade handling message retrieval logic.
     * @param userService   Service to retrieve user-related data.
     */
    @Autowired
    public MessageControllerV1(MessageControllerFacade messageFacade,
                               UserService userService) {
        this.messageFacade = messageFacade;
        this.userService = userService;
    }

    /**
     * Returns a list of messages exchanged between the authenticated user and the target user.
     *
     * @param targetUserId ID of the user with whom messages are exchanged.
     * @param principal    Principal representing the currently authenticated user.
     * @return A list of {@link MessageApiDTO} containing message details.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageApiDTO>> getMessages(@RequestParam("targetUserId") int targetUserId, Principal principal) {
        logger.debug("Getting messages to {}={}", principal.getName(), targetUserId);
        return ResponseEntity.ok(
                messageFacade.getMessageProvider().provide(
                        userService.findByUsername(principal.getName()).getId(),
                        targetUserId
                )
        );
    }
}
