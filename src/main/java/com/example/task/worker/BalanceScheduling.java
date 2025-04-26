package com.example.task.worker;

import com.example.task.repository.UserRepository;
import com.example.task.utils.BalanceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class BalanceScheduling {

    private final BalanceUtils balanceUtils;
    private final UserRepository userRepository;

    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void increaseUsersBalance() {

        userRepository.findAll().forEach(user -> {

            BigDecimal bigDecimal = balanceUtils.getInitialBalance(user.getId()).multiply(BigDecimal.valueOf(2.07));
            BigDecimal newBalance = user.getAccount().getBalance().multiply(BigDecimal.valueOf(1.1));

            if (bigDecimal.compareTo(newBalance) < 0) {
                log.warn("The increased amount {} will be more than 207 percent {} of the initial deposit.", newBalance, bigDecimal);
            } else {
                log.info("User's balance {} was increased and now {}", user.getName(), user.getAccount().getBalance());
                user.getAccount().setBalance(newBalance);
                userRepository.save(user);
            }

        });
    }
}
