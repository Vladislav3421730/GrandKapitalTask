package com.example.task;

import com.example.task.dto.request.LoginRequestDto;
import com.example.task.dto.request.TransferRequestDto;
import com.example.utils.Endpoints;
import com.example.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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
public class TransferControllerTests {

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static LoginRequestDto loginRequestDto;
    private static TransferRequestDto transferRequestDto;
    private static TransferRequestDto transferRequestDtoWithTheSameUser;
    private static TransferRequestDto transferRequestDtoWithInvalidValue;
    private static TransferRequestDto transferRequestDtoWithMaxValue;
    private static TransferRequestDto transferRequestDtoWithInvalidId;

    @BeforeAll
    static void setup() {
        loginRequestDto = new LoginRequestDto("vlad@gmail.com", "q1w2e3");
        transferRequestDto = new TransferRequestDto(2L, BigDecimal.valueOf(100L));
        transferRequestDtoWithTheSameUser = new TransferRequestDto(1L, BigDecimal.valueOf(100L));
        transferRequestDtoWithInvalidValue = new TransferRequestDto(2L, BigDecimal.ZERO);
        transferRequestDtoWithInvalidId = new TransferRequestDto(Long.MIN_VALUE, BigDecimal.valueOf(100L));
        transferRequestDtoWithMaxValue = new TransferRequestDto(2L, BigDecimal.valueOf(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("Test transfer money with valid data")
    void testTransferMoney() throws Exception {

        String accessToken = TokenUtils.getAccessTokenFromRequest(mockMvc, loginRequestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_TRANSFER_MONEY)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequestDto))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.targetUserId", is(2)))
                .andExpect(jsonPath("$.value", is(transferRequestDto.getValue().intValue())))
                .andExpect(jsonPath("$.remainingAmount", is(400.23)));
    }

    @Test
    @DisplayName("Test transfer money with the same user")
    void testTransferMoneyWithTheSameUser() throws Exception {

        String accessToken = TokenUtils.getAccessTokenFromRequest(mockMvc, loginRequestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_TRANSFER_MONEY)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequestDtoWithTheSameUser))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.code", notNullValue()));
    }

    @Test
    @DisplayName("Test transfer money with invalid value")
    void testTransferMoneyWithInvalidValue() throws Exception {

        String accessToken = TokenUtils.getAccessTokenFromRequest(mockMvc, loginRequestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_TRANSFER_MONEY)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequestDtoWithInvalidValue))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", notNullValue()))
                .andExpect(jsonPath("$.errors.value", notNullValue()))
                .andExpect(jsonPath("$.code", is(400)));
    }

    @Test
    @DisplayName("Test transfer money with invalid user id")
    void testTransferMoneyWithInvalidUserId() throws Exception {

        String accessToken = TokenUtils.getAccessTokenFromRequest(mockMvc, loginRequestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_TRANSFER_MONEY)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequestDtoWithInvalidId))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.code", is(404)));
    }

    @Test
    @DisplayName("Test transfer money with value more then user's balance")
    void testTransferMoneyWithMaxValue() throws Exception {

        String accessToken = TokenUtils.getAccessTokenFromRequest(mockMvc, loginRequestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_TRANSFER_MONEY)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequestDtoWithMaxValue))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.code", is(400)));
    }



}
