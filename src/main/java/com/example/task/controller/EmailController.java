package com.example.task.controller;

import com.example.task.dto.error.AppErrorDto;
import com.example.task.dto.request.CreateEmailRequestDto;
import com.example.task.dto.request.DeleteEmailRequestDto;
import com.example.task.dto.request.ReplaceEmailRequestDto;
import com.example.task.service.EmailService;
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
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Email Controller", description = "Handles operations related to user's emails: create, delete, update")
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Operation was successful"),
        @ApiResponse(responseCode = "204", description = "Operation was successful"),
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
public class EmailController {

    private final EmailService emailService;
    private static final String USER_ID = "userId";

    @PostMapping
    @Operation(summary = "Create Email", description = "Creates a new email for the authenticated user")
    public ResponseEntity<Void> createEmail(
            HttpServletRequest request,
            @Valid @RequestBody CreateEmailRequestDto createEmailRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        emailService.save(userId, createEmailRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    @Operation(summary = "Delete Email", description = "Deletes an existing email for the authenticated user")
    public ResponseEntity<Void> deleteEmail(
            HttpServletRequest request,
            @Valid @RequestBody DeleteEmailRequestDto deleteEmailRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        emailService.delete(userId, deleteEmailRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(summary = "Change Email", description = "Replaces an existing email with a new one for the authenticated user")
    public ResponseEntity<Void> changeEmail(
            HttpServletRequest request,
            @Valid @RequestBody ReplaceEmailRequestDto replaceEmailRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        emailService.replace(userId, replaceEmailRequestDto);
        return ResponseEntity.noContent().build();
    }
}
