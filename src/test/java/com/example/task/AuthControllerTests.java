package com.example.task;

import com.example.task.dto.request.LoginRequestDto;
import com.example.utils.Endpoints;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests {

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static LoginRequestDto loginRequestDto;
    private static LoginRequestDto invalidCredLoginRequestDto;
    private static LoginRequestDto invalidDataLoginRequestDto;

    @BeforeAll
    static void setup() {
        loginRequestDto = new LoginRequestDto("vlad@gmail.com", "q1w2e3");
        invalidCredLoginRequestDto = new LoginRequestDto("wrongEmail@gmail.com", "q1w2e3");
        invalidDataLoginRequestDto = new LoginRequestDto("invalidEmail", "");
    }

    @Test
    @DisplayName("Test login and get access token")
    void testCreateAuthToken() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.accessToken", notNullValue()));
    }

    @Test
    @DisplayName("Test login and get access with invalid cred")
    void testCreateWithInvalidCred() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCredLoginRequestDto))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.code", notNullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("Test login and get access with invalid data")
    void testCreateWithInvalidData() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDataLoginRequestDto))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errors", notNullValue()))
                .andExpect(jsonPath("$.errors.email", notNullValue()))
                .andExpect(jsonPath("$.errors.password", notNullValue()));
    }

}
