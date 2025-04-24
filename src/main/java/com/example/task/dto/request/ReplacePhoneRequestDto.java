package com.example.task.dto.request;

import com.example.task.validation.PhoneNumber;
import lombok.Data;

@Data
public class ReplacePhoneRequestDto {

    @PhoneNumber
    private String oldPhone;

    @PhoneNumber
    private String newPhone;
}
