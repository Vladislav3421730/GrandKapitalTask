package com.example.task.service.Impl;

import com.example.task.dto.request.CreateEmailRequestDto;
import com.example.task.dto.request.DeleteEmailRequestDto;
import com.example.task.dto.request.ReplaceEmailRequestDto;
import com.example.task.exception.DeletingException;
import com.example.task.exception.EmailAlreadyExistException;
import com.example.task.exception.EmailNotFoundException;
import com.example.task.exception.UserNotFoundException;
import com.example.task.model.EmailData;
import com.example.task.model.User;
import com.example.task.repository.EmailDataRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final EmailDataRepository emailDataRepository;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "user", key = "#id"),
            @CacheEvict(value = "users", allEntries = true)
    })
    public void save(Long id, CreateEmailRequestDto createEmailRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User with id {} wasn't found", id);
            throw new UserNotFoundException(String.format("user with id %d wasn't found", id));
        });

        String email = createEmailRequestDto.getEmail();
        if (user.getEmailData().stream().anyMatch(data -> data.getEmail().equals(email))) {
            log.error("Email {} already exist in account", email);
            throw new EmailAlreadyExistException(String.format("Email %s already exist in your account", email));
        }

        if (emailDataRepository.existsByEmail(email)) {
            log.error("Email {} already exist", email);
            throw new EmailAlreadyExistException(String.format("Email %s already exist", email));
        }

        EmailData emailData = new EmailData(email, user);
        emailDataRepository.save(emailData);
        log.info("email {} was successfully added to user {}", emailData.getEmail(), user.getId());
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "user", key = "#userId"),
            @CacheEvict(value = "users", allEntries = true)
    })
    public void delete(Long userId, DeleteEmailRequestDto deleteEmailRequestDto) {

        String email = deleteEmailRequestDto.getEmail();

        EmailData emailData = emailDataRepository.findByUserIdAndEmail(userId, email).orElseThrow(() -> {
            log.error("Email {} was not found", email);
            throw new EmailNotFoundException(String.format("Email %s was not found", email));
        });

        if (emailData.getUser().getEmailData().size() == 1) {
            log.error("Impossible delete the last email {}", email);
            throw new DeletingException(String.format("Impossible delete the last email %s", email));
        }

        emailDataRepository.delete(emailData);
        log.info("Email {} was successfully deleted", email);

    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "user", key = "#userId"),
            @CacheEvict(value = "users", allEntries = true)
    })
    public void replace(Long userId, ReplaceEmailRequestDto replaceEmailRequestDto) {

        String oldEmail = replaceEmailRequestDto.getOldEmail();
        String newEmail = replaceEmailRequestDto.getNewEmail();

        EmailData emailData = emailDataRepository.findByUserIdAndEmail(userId, oldEmail).orElseThrow(() -> {
            log.error("Old email {} was not found", oldEmail);
            throw new EmailNotFoundException(String.format("Old email %s was not found", oldEmail));
        });

        if (emailDataRepository.existsByEmail(newEmail)) {
            log.error("New email {} already exist", newEmail);
            throw new EmailAlreadyExistException(String.format("New email %s already exist", newEmail));
        }

        emailData.setEmail(newEmail);
        emailDataRepository.save(emailData);
        log.info("Email {} was successfully replaced on {}", oldEmail, newEmail);

    }
}
