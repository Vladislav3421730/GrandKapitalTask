package com.example.task.service;

import com.example.task.dto.request.*;

public interface PhoneService {
    void save(Long id, CreatePhoneRequestDto createEmailRequestDto);

    void delete(Long userId, DeletePhoneRequestDto deleteEmailRequestDto);

    void replace(Long userId, ReplacePhoneRequestDto replaceEmailRequestDto);
}
