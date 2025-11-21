package ru.defix.chat.api.controller;

import ru.defix.chat.api.dto.response.ChatApiDTO;
import ru.defix.chat.api.facade.ChatControllerFacade;
import ru.defix.chat.api.dto.response.ChatDetailsApiDTO;
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
 * REST controller responsible for handling chat-related API requests.
 * Provides endpoints to fetch the current user's chats and chat metadata.
 */
@RestController
@RequestMapping("/api/v1/chats")
public class ChatControllerV1 {

    private static final Logger logger = LoggerFactory.getLogger(ChatControllerV1.class);
    private final ChatControllerFacade chatFacade;
    private final UserService userService;

    /**
     * Constructs a new ChatControllerV1.
     *
     * @param chatFacade  The facade that handles chat business logic.
     * @param userService The service to manage user information.
     */
    @Autowired
    public ChatControllerV1(ChatControllerFacade chatFacade,
                            UserService userService) {
        this.chatFacade = chatFacade;
        this.userService = userService;
    }

    /**
     * Retrieves a list of chats for the currently authenticated user.
     *
     * @param principal The security principal representing the current user.
     * @return A list of chat objects the user participates in.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChatApiDTO>> getChats(Principal principal) {
        logger.debug("Getting chats to {}...", principal.getName());
        return ResponseEntity.ok(chatFacade.getChatProvider().provide(
                userService.findByUsername(principal.getName()).getId())
        );
    }

    /**
     * Retrieves chat metadata between the current user and a target user by their ID.
     *
     * @param targetUserId The ID of the user to retrieve chat metadata with.
     * @return Chat metadata containing the username and ID of the target user.
     */
    @GetMapping("/metadata")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChatDetailsApiDTO> getChatInfo(@RequestParam("targetUserId") int targetUserId) {
        logger.debug("Getting chat info to: {}", targetUserId);
        return ResponseEntity.ok(new ChatDetailsApiDTO(userService.findById(targetUserId).getUsername(), targetUserId));
    }
}