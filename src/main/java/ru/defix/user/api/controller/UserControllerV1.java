package ru.defix.user.api.controller;

import ru.defix.user.service.UserService;
import ru.defix.user.api.dto.response.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * REST controller for user-related operations.
 * Provides endpoint to retrieve basic information about the authenticated user.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    private final UserService userService;

    /**
     * Constructs a UserControllerV1 with the given UserService.
     *
     * @param userService Service to handle user data.
     */
    @Autowired
    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns metadata about the currently authenticated user.
     *
     * @param principal Principal representing the authenticated user.
     * @return A {@link UserDTO} containing username and user ID.
     */
    @GetMapping("/metadata")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) {
        return ResponseEntity.ok(new UserDTO(
                        principal.getName(),
                        userService.findByUsername(principal.getName()).getId()
                )
        );
    }
}
