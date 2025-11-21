package ru.defix.chat.api.controller;

import ru.defix.chat.api.facade.EncryptionControllerFacade;
import ru.defix.chat.api.dto.request.ChatKeysApiDTO;
import ru.defix.chat.api.dto.response.ChatKeysCreationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.defix.user.service.UserService;

import java.security.Principal;

/**
 * REST controller for managing user encryption keys.
 * Provides endpoints to fetch and register public/private keys.
 */
@RestController
@RequestMapping("/api/v1/encryption")
public class EncryptionControllerV1 {

    private final EncryptionControllerFacade encryptionFacade;
    private final UserService userService;

    /**
     * Constructs an EncryptionControllerV1 with the necessary dependencies.
     *
     * @param encryptionFacade Facade handling encryption-related logic.
     * @param userService      Service for retrieving user information.
     */
    @Autowired
    public EncryptionControllerV1(EncryptionControllerFacade encryptionFacade,
                                  UserService userService) {
        this.encryptionFacade = encryptionFacade;
        this.userService = userService;
    }

    /**
     * Returns the public encryption key of the specified user.
     *
     * @param userId ID of the user whose public key is requested.
     * @return Public key as a String.
     */
    @GetMapping("/users/public/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getPublicKey(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(encryptionFacade.getPublicKeyProvider().provide(userId));
    }

    /**
     * Returns the private encryption key of the currently authenticated user.
     *
     * @param principal Principal representing the authenticated user.
     * @return Private key as a String.
     */
    @GetMapping("/users/private")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getPrivateKey(Principal principal) {
        return ResponseEntity.ok(encryptionFacade.getPrivateKeyProvider().provide(
                userService.findByUsername(principal.getName()).getId()
        ));
    }

    /**
     * Registers a pair of public and private keys for the authenticated user.
     *
     * @param keys      DTO containing public and private keys.
     * @param principal Principal representing the authenticated user.
     * @return Empty response with 204 No Content status.
     */
    @PostMapping("/users/keys")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> addKeysPair(@RequestBody ChatKeysApiDTO keys, Principal principal) {
        encryptionFacade.getKeysCreator().create(new ChatKeysCreationDetails(
                userService.findByUsername(principal.getName()).getId(),
                keys.getPrivateKey(),
                keys.getPublicKey()
        ));
        return ResponseEntity.noContent().build();
    }
}