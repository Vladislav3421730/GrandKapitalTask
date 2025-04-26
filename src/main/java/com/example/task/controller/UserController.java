package com.example.task.controller;

import com.example.task.dto.FilterDto;
import com.example.task.dto.PageDto;
import com.example.task.dto.UserDto;
import com.example.task.dto.error.AppErrorDto;
import com.example.task.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User Controller", description = "Handles operations related to users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get list of users", description = "Returns a paginated list of users with optional filtering by date of birth, email, phone, or name")
    public ResponseEntity<PageDto<UserDto>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(value = "dateOfBirth", required = false) String dateOfBirth,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "name", required = false) String name ) {
        FilterDto filterDto = new FilterDto(dateOfBirth, email, name, phone);
        PageDto<UserDto> users = userService.findAllByFilters(filterDto, PageRequest.of(page, pageSize));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user by id", description = "Returns a user")
    @ApiResponse(
            responseCode = "404",
            description = "Data not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorDto.class))
    )
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
}
