package com.example.task.service.Impl;

import com.example.task.dto.FilterDto;
import com.example.task.dto.UserDto;
import com.example.task.mapper.UserMapper;
import com.example.task.model.User;
import com.example.task.repository.UserRepository;
import com.example.task.service.UserService;
import com.example.task.utils.PredicateFormationAssistant;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserDto> findAllByFilters(FilterDto filters, PageRequest pageRequest) {

        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = PredicateFormationAssistant.createFromFilterDto(filters, cb, root);
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(spec, pageRequest)
                .map(UserMapper::map);
    }
}
