package com.example.task.service.Impl;

import com.example.task.dto.request.TransferRequestDto;
import com.example.task.dto.response.TransferResponseDto;
import com.example.task.exception.TransferException;
import com.example.task.exception.UserNotFoundException;
import com.example.task.model.User;
import com.example.task.repository.UserRepository;
import com.example.task.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransferResponseDto transfer(Long userId, TransferRequestDto transferRequestDto) {

        Long targetUserId = transferRequestDto.getUserId();
        if(userId.equals(targetUserId)) {
            log.error("User with id {} attempted to transfer money to themselves, which is not allowed", userId);
            throw new TransferException(String.format("User with id %d cannot transfer money to themselves", userId));
        }

        User user = userRepository.findByIdForUpdate(userId).orElseThrow(() -> {
            log.error("User with id {} wasn't found, transfer is impossible", userId);
            throw new UserNotFoundException(String.format("User with id %d wasn't found, transfer is impossible", userId));
        });

        User targetUser = userRepository.findByIdForUpdate(targetUserId).orElseThrow(() -> {
            log.error("Target user with id {} wasn't found, transfer is impossible", targetUserId);
            throw new UserNotFoundException(String.format("Target user with id %d wasn't found, transfer is impossible", targetUserId));
        });

        BigDecimal value = transferRequestDto.getValue();
        BigDecimal remainingAmount = user.getAccount().getBalance().subtract(value);

        log.info("Attempting to transfer {} from user {} to user {}", value, userId, targetUserId);

        if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
            log.error("User {} has insufficient balance: trying to transfer {}, but only has {}", userId, value, user.getAccount().getBalance());
            throw new TransferException(String.format("Insufficient balance for user with id %d. Available: %s, Required: %s",
                    userId, user.getAccount().getBalance(), value));
        }

        user.getAccount().setBalance(remainingAmount);
        targetUser.getAccount().setBalance(targetUser.getAccount().getBalance().add(value));

        userRepository.save(user);
        userRepository.save(targetUser);

        log.info("Successfully transferred {} from user {} to user {}", value, userId, targetUserId);

        return TransferResponseDto.builder()
                .userId(userId)
                .targetUserId(targetUserId)
                .value(value)
                .remainingAmount(remainingAmount)
                .build();
    }
}
