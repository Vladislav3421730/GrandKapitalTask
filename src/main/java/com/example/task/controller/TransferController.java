package com.example.task.controller;

import com.example.task.dto.request.TransferRequestDto;
import com.example.task.dto.response.TransferResponseDto;
import com.example.task.dto.request.CreateEmailRequestDto;
import com.example.task.service.TransferService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    private static final String USER_ID = "userId";

    @PostMapping
    public ResponseEntity<TransferResponseDto> transfer(
            HttpServletRequest request,
            @Valid @RequestBody TransferRequestDto transferRequestDto) {
        Long userId = (Long) request.getAttribute(USER_ID);
        TransferResponseDto transferResponseDto = transferService.transfer(userId, transferRequestDto);
        return ResponseEntity.ok(transferResponseDto);
    }
}
