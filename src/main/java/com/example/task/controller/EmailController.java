package com.example.task.controller;

import com.example.task.dto.request.CreateEmailRequestDto;
import com.example.task.dto.request.DeleteEmailRequestDto;
import com.example.task.dto.request.ReplaceEmailRequestDto;
import com.example.task.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private static final String USER_ID = "userId";

    @PostMapping
    public ResponseEntity<Void> createEmail(
            HttpServletRequest request,
            @Valid @RequestBody CreateEmailRequestDto createEmailRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        emailService.save(userId, createEmailRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmail(
            HttpServletRequest request,
            @Valid @RequestBody DeleteEmailRequestDto deleteEmailRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        emailService.delete(userId, deleteEmailRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> changeEmail(
            HttpServletRequest request,
            @Valid @RequestBody ReplaceEmailRequestDto replaceEmailRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        emailService.replace(userId, replaceEmailRequestDto);
        return ResponseEntity.noContent().build();
    }


}
