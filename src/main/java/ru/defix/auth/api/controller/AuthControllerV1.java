package ru.defix.auth.api.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ru.defix.auth.service.dto.RegistrationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.defix.auth.api.dto.request.Credentials;
import ru.defix.auth.service.CookieAuthenticationAdapter;
import ru.defix.auth.service.authentication.AuthenticationService;
import ru.defix.auth.service.registration.RegistrationService;

import java.security.Principal;
import java.util.Map;

/**
 * Controller for managing user authentication.
 * Handles login, registration, logout, and authorization check requests.
 */
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthControllerV1 {

    private final AuthenticationService authentication;
    private final RegistrationService registration;
    private static final Logger logger = LoggerFactory.getLogger(AuthControllerV1.class);

    /**
     * Constructor to initialize the controller with authentication and registration services.
     *
     * @param authentication Authentication service
     * @param registration Registration service
     */
    @Autowired
    public AuthControllerV1(AuthenticationService authentication, RegistrationService registration) {
        this.authentication = authentication;
        this.registration = registration;
    }

    /**
     * Method to authenticate a user with provided credentials.
     * Returns an access token if authentication is successful.
     *
     * @param credentials User credentials (username and password)
     * @param response HTTP response to add the authentication token as a cookie
     * @return ResponseEntity containing the authentication token on successful authentication
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody Credentials credentials, HttpServletResponse response) {
        String token = authentication.authenticate(credentials);
        response.addCookie(CookieAuthenticationAdapter.adaptLoginCookie(token));

        logger.debug("Success Authenticate!");
        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }

    /**
     * Method to register a new user.
     * Accepts registration details and creates a new user.
     *
     * @param details Registration details (username and password)
     * @return ResponseEntity with no content and HTTP status 204 No Content
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationDetails details) {
        logger.debug(details.getUsername() + details.getPassword());
        registration.register(details);
        return ResponseEntity.noContent().build();
    }

    /**
     * Method to log the user out. Requires the user to be authenticated.
     * Removes the session cookie and ends the session.
     *
     * @param res HTTP response to remove the logout cookie
     * @return ResponseEntity with no content and HTTP status 204 No Content
     */
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        res.addCookie(CookieAuthenticationAdapter.adaptLogoutCookie());
        return ResponseEntity.noContent().build();
    }

    /**
     * Method to check if the user is authorized.
     * Returns true if the user is authenticated, otherwise false.
     *
     * @param principal The principal representing the authenticated user
     * @return ResponseEntity with a boolean value indicating if the user is authenticated
     */
    @GetMapping("/authorized")
    public ResponseEntity<Boolean> authorized(Principal principal) {
        return ResponseEntity.ok(principal != null);
    }
}