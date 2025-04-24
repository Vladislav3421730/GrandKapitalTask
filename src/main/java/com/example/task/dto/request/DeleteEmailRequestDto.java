package com.example.task.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteEmailRequestDto {

    @NotBlank(message = "email must be not blank")
    @Email(message = "email must be contains @")
    private String email;
}
