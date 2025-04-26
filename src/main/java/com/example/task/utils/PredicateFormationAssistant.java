package com.example.task.utils;

import com.example.task.dto.FilterDto;
import com.example.task.model.EmailData;
import com.example.task.model.PhoneData;
import com.example.task.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@UtilityClass
public class PredicateFormationAssistant {

    private final static String TABLE_EMAIL_DATA = "emailData";
    private final static String TABLE_PHONE_DATA = "phoneData";
    private final static String COLUMN_EMAIL = "email";
    private final static String COLUMN_PHONE = "phone";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_DATE = "dateOfBirth";

    public List<Predicate> createFromFilterDto(FilterDto filterDto, CriteriaBuilder cb, Root<User> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filterDto.email() != null && !filterDto.email().isBlank()) {
            log.info("filter email (allowed in search input) {} was added to predicates", filterDto.email());
            Join<User, EmailData> emailJoin = root.join(TABLE_EMAIL_DATA);
            predicates.add(cb.equal(emailJoin.get(COLUMN_EMAIL), filterDto.email()));
        }

        if (filterDto.phone() != null && !filterDto.phone().isBlank()) {
            log.info("filter phone {} was added to predicates", filterDto.phone());
            Join<User, PhoneData> phoneJoin = root.join(TABLE_PHONE_DATA);
            predicates.add(cb.equal(phoneJoin.get(COLUMN_PHONE), filterDto.phone()));
        }

        if (filterDto.name() != null && !filterDto.name().isBlank()) {
            log.info("filter name {} was added to predicates", filterDto.name());
            predicates.add(cb.like(cb.lower(root.get(COLUMN_NAME)), filterDto.name().toLowerCase() + "%"));
        }

        if (filterDto.dateOfBirth() != null && !filterDto.dateOfBirth().isBlank()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate dateOfBirth = LocalDate.parse(filterDto.dateOfBirth(), formatter);
                log.info("filter dateOfBirth {} was added to predicates", dateOfBirth);
                predicates.add(cb.greaterThan(root.get(COLUMN_DATE), dateOfBirth));
            } catch (DateTimeParseException e) {
                log.error("Failed to parse dateOfBirth: {}", filterDto.dateOfBirth());
            }
        }
        return predicates;
    }
}
