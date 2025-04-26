package com.example.task.controller;

import com.example.task.dto.error.AppErrorDto;
import com.example.task.dto.request.TransferRequestDto;
import com.example.task.dto.response.TransferResponseDto;
import com.example.task.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Transfer Controller", description = "Handles money transfers between users")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operation was successful"),
        @ApiResponse(
                responseCode = "404",
                description = "Data not found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorDto.class))
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorDto.class))
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorDto.class))
        )
})
public class TransferController {

    private final TransferService transferService;

    private static final String USER_ID = "userId";

    @PostMapping
    @Operation(summary = "Transfer Money", description = "Transfers money from authenticated user to another user")
    public ResponseEntity<TransferResponseDto> transfer(
            HttpServletRequest request,
            @Valid @RequestBody TransferRequestDto transferRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        TransferResponseDto transferResponseDto = transferService.transfer(userId, transferRequestDto);
        return ResponseEntity.ok(transferResponseDto);
    }
}
