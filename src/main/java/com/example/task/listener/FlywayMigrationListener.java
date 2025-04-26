package com.example.task.listener;

import com.example.task.model.User;
import com.example.task.repository.UserRepository;
import com.example.task.utils.BalanceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlywayMigrationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Flyway flyway;
    private final BalanceUtils balanceUtils;
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (flyway.info().current().getVersion() != null) {
            log.info("All Flyway migrations completed successfully.");
            log.info("Application context refreshed. Writing initial balances to file...");

            List<User> users = userRepository.findAll();
            balanceUtils.saveInitialBalance(users);

            log.info("Initial balances written to file successfully.");
        }

    }
}
