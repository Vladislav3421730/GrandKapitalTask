package com.example.task.dto.request;

import com.example.task.validation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePhoneRequestDto {

    @PhoneNumber
    private String phone;
}
