package ru.defix.tests.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.defix.database.postgresql.entity.Role;
import ru.defix.database.postgresql.entity.User;
import ru.defix.chat.service.provider.BiProvider;
import ru.defix.chat.exception.NoMessagesException;
import ru.defix.chat.api.dto.response.MessageApiDTO;
import ru.defix.tests.TestUtils;
import ru.defix.user.service.UserService;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
public class MessageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private BiProvider<List<MessageApiDTO>, Integer> messageProvider;

    @Nested
    class SuccessfulCases {
        @WithMockUser(username = "user")
        @Test
        public void getMessagesTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(), Collections.singleton(new Role(1, "USER")), new HashSet<>()
            );
            MessageApiDTO messageApiDTO = new MessageApiDTO(
                    "msg", LocalDateTime.now(), 2
            );

            String expectedJson = String.format("""
                            [{
                             "message":"%s",
                             "createdAt":"%s",
                             "senderId":%d
                            }]
                            """, messageApiDTO.getMessage(),
                    TestUtils.localDateTimeToString(messageApiDTO.getCreatedAt()),
                    messageApiDTO.getSenderId());

            when(userService.findByUsername("user")).thenReturn(user);
            when(messageProvider.provide(user.getId(), 2)).thenReturn(List.of(messageApiDTO));
            mockMvc.perform(get("/api/v1/messages")
                            .param("targetUserId", "2")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    class ParamErrorCases {
        @Test
        @WithMockUser
        public void getMessagesIfParamNull() throws Exception {
            mockMvc.perform(get("/api/v1/messages")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class AuthErrorCases {
        @Test
        public void getMessagesIfPrincipalNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/messages")
                            .param("targetUserId", "1")
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class NotFoundErrorCases {
        @Test
        @WithMockUser(username = "user")
        public void getMessagesIfEmptyTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(), Collections.singleton(new Role(1, "USER")), new HashSet<>()
            );

            when(userService.findByUsername("user")).thenReturn(user);
            when(messageProvider.provide(user.getId(), 2)).thenThrow(new NoMessagesException());
            mockMvc.perform(get("/api/v1/messages")
                            .param("targetUserId", "2")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }
    }
}
