package com.example.task.service.Impl;

import com.example.task.dto.request.LoginRequestDto;
import com.example.task.dto.response.JwtResponseDto;
import com.example.task.exception.LoginFailedException;
import com.example.task.exception.UserNotFoundException;
import com.example.task.model.User;
import com.example.task.repository.UserRepository;
import com.example.task.service.AuthService;
import com.example.task.utils.JwtAccessTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenUtils jwtAccessTokenUtils;

    @Override
    public JwtResponseDto createAuthToken(LoginRequestDto user) {
        try {
            log.info("Attempting authentication for user: {}", user.getEmail());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            log.error("error: {}", badCredentialsException.getMessage());
            throw new LoginFailedException("Invalid username or password");
        }

        User userDB = userRepository.findByEmail(user.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("User with email %s was not found", user.getEmail())));

        log.info("User {} authenticated successfully", user.getEmail());
        return new JwtResponseDto(jwtAccessTokenUtils.generateAccessToken(userDB, user.getEmail()));
    }
}
