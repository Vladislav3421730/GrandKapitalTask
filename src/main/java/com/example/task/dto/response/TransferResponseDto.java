package com.example.task.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferResponseDto {

    private Long userId;
    private Long targetUserId;
    private BigDecimal value;
    private BigDecimal remainingAmount;
}
