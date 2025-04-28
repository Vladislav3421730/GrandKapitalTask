package com.example.task.controller;

import com.example.task.dto.error.AppErrorDto;
import com.example.task.dto.request.CreatePhoneRequestDto;
import com.example.task.dto.request.DeletePhoneRequestDto;
import com.example.task.dto.request.ReplacePhoneRequestDto;
import com.example.task.service.PhoneService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/phone")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Phone Controller", description = "Handles operations related to user's phones: create, delete, update")
@ApiResponses({
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
public class PhoneController {

    private final PhoneService phoneService;
    private static final String USER_ID = "userId";

    @PostMapping
    @Operation(summary = "Create Phone", description = "Creates a new phone for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Operation was successful")
    public ResponseEntity<Void> createPhone(
            HttpServletRequest request,
            @Valid @RequestBody CreatePhoneRequestDto createPhoneRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        phoneService.save(userId, createPhoneRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    @Operation(summary = "Delete Phone", description = "Deletes an existing phone for the authenticated user")
    @ApiResponse(responseCode = "204", description = "Operation was successful")
    public ResponseEntity<Void> deletePhone(
            HttpServletRequest request,
            @Valid @RequestBody DeletePhoneRequestDto deletePhoneRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        phoneService.delete(userId, deletePhoneRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(summary = "Change Phone", description = "Replaces an existing phone with a new one for the authenticated user")
    @ApiResponse(responseCode = "204", description = "Operation was successful")
    public ResponseEntity<Void> changePhone(
            HttpServletRequest request,
            @Valid @RequestBody ReplacePhoneRequestDto replacePhoneRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        phoneService.replace(userId, replacePhoneRequestDto);
        return ResponseEntity.noContent().build();
    }

}
