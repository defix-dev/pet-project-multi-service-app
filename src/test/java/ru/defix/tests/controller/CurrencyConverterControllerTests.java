package ru.defix.tests.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
@Disabled
public class CurrencyConverterControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class ParamErrorCases {
        @Test
        @WithMockUser
        public void getCurrencyInfoIfParamNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/currency_converter")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class AuthErrorCases {
        @Test
        public void getCurrencyInfoIfUnauthorizedTest() throws Exception {
            mockMvc.perform(get("/api/v1/currency_converter")
                            .param("fromType", "t1")
                            .param("toType", "t2")
                            .param("fromValue", "123")
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }
    }
}
