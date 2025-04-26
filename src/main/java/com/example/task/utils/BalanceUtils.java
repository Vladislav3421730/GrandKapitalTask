package com.example.task.utils;

import com.example.task.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceUtils {

    private final ObjectMapper objectMapper;
    private static final String BALANCE_FILE_PATH = "balances.json";

    public BigDecimal getInitialBalance(Long userId) {
        try {
            Map<Long, BigDecimal> balances = objectMapper.readValue(new File(BALANCE_FILE_PATH), new TypeReference<Map<Long, BigDecimal>>() {});
            return balances.getOrDefault(userId, BigDecimal.ZERO);
        } catch (IOException e) {
            log.error("Error reading balance file", e);
            return BigDecimal.ZERO;
        }
    }

    public void saveInitialBalance(List<User> users) {
        try {
            File balanceFile = new File(BALANCE_FILE_PATH);

            if (balanceFile.exists() && balanceFile.length() > 0) {
                log.info("Balance file already exists and is not empty. Skipping saving initial balances.");
                return;
            }

            Map<Long, BigDecimal> balances = users.stream()
                    .collect(Collectors.toMap(
                            User::getId,
                            user -> user.getAccount().getBalance()
                    ));
            objectMapper.writeValue(balanceFile, balances);
            log.info("Initial balances saved successfully.");
        } catch (IOException e) {
            log.error("Error saving balance to file", e);
        }
    }

}
