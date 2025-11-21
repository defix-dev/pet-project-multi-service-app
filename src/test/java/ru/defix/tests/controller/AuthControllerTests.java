package ru.defix.tests.controller;

import ru.defix.auth.api.dto.request.Credentials;
import ru.defix.auth.service.authentication.AuthenticationService;
import ru.defix.auth.service.dto.RegistrationDetails;
import ru.defix.auth.service.registration.RegistrationService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private RegistrationService registrationService;

    @Nested
    class SuccessfulCases {
        @Test
        public void loginTest() throws Exception {
            Credentials credentials = new Credentials(
                    "username", "password"
            );

            when(authenticationService.authenticate(any(Credentials.class)))
                    .thenReturn("key");

            String jsonBody = String.format("""
                            {
                                "username":"%s",
                                "password":"%s"
                            }""", credentials.getUsername(),
                    credentials.getPassword());

            String expectedJson = """
                    {
                        "token":"key"
                    }
                    """;

            mockMvc.perform(post("/api/v1/authentication/login")
                            .content(jsonBody)
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }

        @Test
        public void registerTest() throws Exception {
            RegistrationDetails details = new RegistrationDetails(
                    "username", "password"
            );

            String jsonBody = String.format("""
                    {
                        "username":"%s",
                        "password":"%s"
                    }
                    """, details.getUsername(),
                    details.getPassword());

            doNothing().when(registrationService).register(details);
            mockMvc.perform(post("/api/v1/authentication/register")
                            .content(jsonBody)
                            .contentType("application/json"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser
        public void logoutTest() throws Exception {
            mockMvc.perform(post("/api/v1/authentication/logout"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser
        public void authorizedTest() throws Exception {
            mockMvc.perform(get("/api/v1/authentication/authorized"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }
    }

    @Nested
    class AuthErrorCases {
        @Test
        public void logoutIfUnauthorizedTest() throws Exception {
            mockMvc.perform(post("/api/v1/authentication/logout"))
                    .andExpect(status().isForbidden());
        }

        @Test
        public void authorizedIfUnauthorizedTest() throws Exception {
            mockMvc.perform(get("/api/v1/authentication/authorized"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        }
    }

    @Nested
    class ParamErrorCases {
        @Test
        public void loginIfParamNullTest() throws Exception {
            mockMvc.perform(post("/api/v1/authentication/login")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void registerIfParamNullTest() throws Exception {
            mockMvc.perform(post("/api/v1/authentication/register")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class ValidErrorCases {
        @Test
        public void loginTestWithInvalidParamsTest() throws Exception {
            when(authenticationService.authenticate(any(Credentials.class)))
                    .thenReturn("key");

            String jsonBody = """
                    {
                        "username":"",
                        "password":""
                    }
                    """;

            mockMvc.perform(post("/api/v1/authentication/login")
                            .content(jsonBody)
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @CsvSource({
                "u, p",
                "us, pa",
                "username, pas",
                "username, pass",
                "username, passw",
                "username, passwo",
                "username, passwor"
        })
        public void registerTestWithInvalidParamsTest(String username, String password) throws Exception {
            String jsonBody = String.format("""
                    {
                        "username":"%s",
                        "password":"%s"
                    }
                    """, username, password);

            doNothing().when(registrationService).register(new RegistrationDetails(
                    username, password
            ));
            mockMvc.perform(post("/api/v1/authentication/register")
                            .content(jsonBody)
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }
}
