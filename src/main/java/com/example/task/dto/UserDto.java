package com.example.task.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String birthday;
    private BigDecimal balance;
    private List<String> emails;
    private List<String> phones;

}
