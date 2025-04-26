package com.example.task.mapper;

import com.example.task.dto.UserDto;
import com.example.task.model.EmailData;
import com.example.task.model.User;

import java.time.format.DateTimeFormatter;

public class UserMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static UserDto map(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .balance(user.getAccount().getBalance())
                .birthday(user.getDateOfBirth().format(formatter))
                .phones(user.getPhoneData().stream().map(phoneData -> "+" + phoneData.getPhone()).toList())
                .emails(user.getEmailData().stream().map(EmailData::getEmail).toList())
                .build();
    }
}
