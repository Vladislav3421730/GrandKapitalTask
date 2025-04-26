package com.example.task.service;

import com.example.task.dto.FilterDto;
import com.example.task.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    Page<UserDto> findAllByFilters(FilterDto filters, PageRequest pageRequest);
}
