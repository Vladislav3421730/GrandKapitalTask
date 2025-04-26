package com.example.task.service.Impl;

import com.example.task.dto.FilterDto;
import com.example.task.dto.PageDto;
import com.example.task.dto.UserDto;
import com.example.task.exception.UserNotFoundException;
import com.example.task.mapper.UserMapper;
import com.example.task.model.User;
import com.example.task.repository.UserRepository;
import com.example.task.service.UserService;
import com.example.task.utils.PredicateFormationAssistant;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "users", key = "{#pageRequest.pageNumber, #pageRequest.pageSize, " +
            "#filters.name(), #filters.email(), #filters.phone(), #filters.dateOfBirth()}")
    public PageDto<UserDto> findAllByFilters(FilterDto filters, PageRequest pageRequest) {

        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = PredicateFormationAssistant.createFromFilterDto(filters, cb, root);
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<UserDto> page = userRepository.findAll(spec, pageRequest)
                .map(UserMapper::map);

        return new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }

    @Override
    @Cacheable(value = "user", key = "{#id}")
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::map)
                .orElseThrow(() -> {
                    log.error("User with id {} wasn't found", id);
                    throw new UserNotFoundException(String.format("user with id %d wasn't found", id));
                });
    }

}
