package com.example.task.service;

import com.example.task.dto.request.LoginRequestDto;
import com.example.task.dto.response.JwtResponseDto;

public interface AuthService {
    JwtResponseDto createAuthToken(LoginRequestDto user);
}
