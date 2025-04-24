package com.example.task.controller;

import com.example.task.dto.error.AppErrorDto;
import com.example.task.dto.request.LoginRequestDto;
import com.example.task.dto.response.JwtResponseDto;
import com.example.task.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates user and returns a Access and Refresh tokens.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid login credentials",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorDto.class))
            )
    })
    public ResponseEntity<JwtResponseDto> createToken(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        log.info(passwordEncoder.encode("q1w2e3"));
        JwtResponseDto jwtResponseDto = authService.createAuthToken(loginRequestDto);
        return ResponseEntity.ok(jwtResponseDto);
    }

}
