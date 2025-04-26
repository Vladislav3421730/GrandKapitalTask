package com.example.task.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {

    @NotNull(message = "userId must be not null")
    private Long userId;

    @DecimalMin(value = "5.00", message = "Minimum amount for transfer is 5.00")
    private BigDecimal value;

}
