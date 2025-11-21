package ru.defix.tests.controller;

import ru.defix.database.postgresql.entity.Role;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.defix.database.postgresql.entity.User;
import ru.defix.chat.service.creator.Creator;
import ru.defix.chat.service.provider.Provider;
import ru.defix.chat.api.dto.response.ChatKeysCreationDetails;
import ru.defix.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
public class EncryptionControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private Provider<String, Integer> privateKeyProvider;

    @MockitoBean
    private Provider<String, Integer> publicKeyProvider;

    @MockitoBean
    private Creator<ChatKeysCreationDetails> keysCreator;

    @Nested
    class SuccessfulCases {
        @Test
        @WithMockUser
        public void getPublicKeyTest() throws Exception {
            when(publicKeyProvider.provide(1)).thenReturn("key");
            mockMvc.perform(get("/api/v1/encryption/users/public/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("key"));
        }

        @Test
        @WithMockUser(username = "user")
        public void getPrivateKeyTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            when(userService.findByUsername("user")).thenReturn(user);
            when(privateKeyProvider.provide(1)).thenReturn("key");
            mockMvc.perform(get("/api/v1/encryption/users/private"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("key"));
        }

        @Test
        @WithMockUser(username = "user")
        public void addKeysPairTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            when(userService.findByUsername("user")).thenReturn(user);

            String jsonBody = """
                    {
                        "publicKey":"123",
                        "privateKey":"123"
                    }
                    """;

            doNothing().when(keysCreator).create(new ChatKeysCreationDetails(
                    1, "123", "123"
            ));
            mockMvc.perform(post("/api/v1/encryption/users/keys")
                            .content(jsonBody)
                            .contentType("application/json"))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    class CommonErrorCases {
        @Test
        @WithMockUser
        public void addKeysPairIfBodyNullTest() throws Exception {
            mockMvc.perform(post("/api/v1/encryption/users/keys")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class AuthErrorCases {
        @Test
        public void getPrivateKeyIfPrincipalNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/encryption/users/private"))
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getPublicKeyIfPrincipalNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/encryption/users/public/1"))
                    .andExpect(status().isForbidden());
        }

        @Test
        public void addKeysPairIfPrincipalNullTest() throws Exception {
            String jsonBody = """
                    {
                        "publicKey":"123",
                        "privateKey":"123"
                    }
                    """;

            mockMvc.perform(post("/api/v1/encryption/users")
                            .content(jsonBody)
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }
    }
}
