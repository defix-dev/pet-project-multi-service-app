package ru.defix.tests.controller;

import ru.defix.tests.TestUtils;
import ru.defix.user.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.defix.database.postgresql.entity.Chat;
import ru.defix.database.postgresql.entity.Message;
import ru.defix.database.postgresql.entity.Role;
import ru.defix.database.postgresql.entity.User;
import ru.defix.chat.service.provider.Provider;
import ru.defix.chat.exception.EmptyChatsException;
import ru.defix.chat.api.dto.response.ChatApiDTO;
import ru.defix.user.service.UserService;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
public class ChatControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private Provider<List<ChatApiDTO>, Integer> chatProvider;

    @Nested
    @DisplayName("Test of success test-providers")
    class SuccessfulCases {
        @Test
        @WithMockUser(username = "user")
        public void getChatsTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            User targetUser = new User(
                    2, "t_user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            Chat chat = new Chat();
            Message lastMessage = new Message(
                    2, "msg", targetUser, chat, LocalDateTime.now()
            );
            chat.setId(1);
            chat.setCreatedAt(LocalDateTime.now());
            chat.setMembers(Set.of(
                    user, targetUser
            ));
            chat.setMessages(Set.of(
                    new Message(
                            1, "msg___one", user, chat, LocalDateTime.now()
                    ),
                    lastMessage
            ));
            String expectedJson = String.format("""
                [{
                 "targetUsername":"%s",
                 "targetId":%d,
                 "lastMessage":"%s",
                 "localDateTime":"%s"
                }]
                """, targetUser.getUsername(),
                    targetUser.getId(),
                    lastMessage.getMessage(),
                    TestUtils.localDateTimeToString(lastMessage.getCreatedAt()));

            when(userService.findByUsername("user")).thenReturn(user);
            when(chatProvider.provide(user.getId())).thenReturn(List.of(
                    new ChatApiDTO(
                            targetUser.getUsername(),
                            targetUser.getId(),
                            lastMessage.getMessage(),
                            lastMessage.getCreatedAt()
                    )
            ));
            mockMvc.perform(get("/api/v1/chats")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }

        @Test
        @WithMockUser(username = "user")
        public void getChatInfoInfo() throws Exception {
            User user = new User(
                    2, "user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            when(userService.findById(2)).thenReturn(user);
            String expectedJson = String.format("""
                {
                    "targetUsername":"%s",
                    "targetId":2
                }
                """, user.getUsername());

            mockMvc.perform(get("/api/v1/chats/metadata")
                            .param("targetUserId", "2"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    @DisplayName("Test of empty data")
    class NotFoundErrorCases {
        @Test
        @WithMockUser(username = "user")
        public void getChatsIfChatsNotFoundTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            when(userService.findByUsername("user")).thenReturn(user);
            when(chatProvider.provide(1)).thenThrow(new EmptyChatsException());
            mockMvc.perform(get("/api/v1/chats")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(username = "user")
        public void getChatsIfUserNotFoundTest() throws Exception {
            when(userService.findByUsername("user")).thenThrow(new UserNotFoundException());
            mockMvc.perform(get("/api/v1/chats")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser
        public void getChatInfoIfUserNotFoundTest() throws Exception {
            when(userService.findById(1)).thenThrow(new UserNotFoundException());
            mockMvc.perform(get("/api/v1/chats/metadata")
                            .param("targetUserId", "1")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Test of auth errors")
    class AuthErrorCases {
        @Test
        public void getChatsIfPrincipalNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/chats")
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class ParamErrorCases {
        @Test
        @WithMockUser
        public void getChatInfoIfParamNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/chats/metadata")
                    .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }
}
