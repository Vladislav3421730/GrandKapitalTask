package com.example.task.service;

import com.example.task.dto.request.CreateEmailRequestDto;
import com.example.task.dto.request.DeleteEmailRequestDto;
import com.example.task.dto.request.ReplaceEmailRequestDto;

public interface EmailService {
    void save(Long id, CreateEmailRequestDto createEmailRequestDto);

    void delete(Long userId, DeleteEmailRequestDto deleteEmailRequestDto);

    void replace(Long userId, ReplaceEmailRequestDto replaceEmailRequestDto);
}
