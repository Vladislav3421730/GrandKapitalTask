package com.example.task.service;

import com.example.task.dto.request.TransferRequestDto;
import com.example.task.dto.response.TransferResponseDto;

public interface TransferService {
    TransferResponseDto transfer(Long userId, TransferRequestDto transferRequestDto);
}
