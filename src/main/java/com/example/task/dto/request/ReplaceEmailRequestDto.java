package com.example.task.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReplaceEmailRequestDto {

    @NotBlank(message = "oldEmail must be not blank")
    @Email(message = "email must be contains @")
    private String oldEmail;

    @NotBlank(message = "newEmail must be not blank")
    @Email(message = "email must be contains @")
    private String newEmail;
}
