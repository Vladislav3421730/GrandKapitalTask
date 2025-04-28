package com.example.utils;

import com.example.task.dto.request.LoginRequestDto;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static String getAccessTokenFromRequest(MockMvc mockMvc, LoginRequestDto loginRequestDto) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.API_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andReturn().getResponse().getContentAsString();

        return JsonPath.parse(response).read("$.accessToken");
    }

}
