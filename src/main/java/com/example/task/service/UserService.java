package com.example.task.service;

import com.example.task.dto.FilterDto;
import com.example.task.dto.PageDto;
import com.example.task.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    PageDto<UserDto> findAllByFilters(FilterDto filters, PageRequest pageRequest);

    UserDto findById(Long id);
}
