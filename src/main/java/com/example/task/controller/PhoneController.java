package com.example.task.controller;

import com.example.task.dto.request.*;
import com.example.task.service.PhoneService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/phone")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;
    private static final String USER_ID = "userId";

    @PostMapping
    public ResponseEntity<Void> createPhone(
            HttpServletRequest request,
            @Valid @RequestBody CreatePhoneRequestDto createPhoneRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        phoneService.save(userId, createPhoneRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePhone(
            HttpServletRequest request,
            @Valid @RequestBody DeletePhoneRequestDto deletePhoneRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        phoneService.delete(userId, deletePhoneRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> changePhone(
            HttpServletRequest request,
            @Valid @RequestBody ReplacePhoneRequestDto replacePhoneRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        phoneService.replace(userId, replacePhoneRequestDto);
        return ResponseEntity.noContent().build();
    }


}
