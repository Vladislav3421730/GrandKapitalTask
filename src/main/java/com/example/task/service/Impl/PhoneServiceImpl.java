package com.example.task.service.Impl;

import com.example.task.dto.request.CreatePhoneRequestDto;
import com.example.task.dto.request.DeletePhoneRequestDto;
import com.example.task.dto.request.ReplacePhoneRequestDto;
import com.example.task.exception.EmailAlreadyExistException;
import com.example.task.exception.EmailNotFoundException;
import com.example.task.exception.PhoneAlreadyExistException;
import com.example.task.exception.UserNotFoundException;
import com.example.task.model.PhoneData;
import com.example.task.model.User;
import com.example.task.repository.PhoneDataRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final UserRepository userRepository;
    private final PhoneDataRepository phoneDataRepository;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "user", key = "#id"),
            @CacheEvict(value = "users", allEntries = true)
    })
    public void save(Long id, CreatePhoneRequestDto createPhoneRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User with id {} wasn't found", id);
            throw new UserNotFoundException(String.format("user with id %d wasn't found", id));
        });

        String phone = createPhoneRequestDto.getPhone();
        if (user.getPhoneData().stream().anyMatch(data -> data.getPhone().equals(phone))) {
            log.error("Phone {} already exist in account", phone);
            throw new PhoneAlreadyExistException(String.format("Phone %s already exist in your account", phone));
        }

        if(phoneDataRepository.existsByPhone(phone)) {
            log.error("Phone {} already exist", phone);
            throw new PhoneAlreadyExistException(String.format("Phone %s already exist", phone));
        }

        PhoneData phoneData = new PhoneData(phone, user);
        phoneDataRepository.save(phoneData);
        log.info("Phone {} was successfully added to user {}", phoneData.getPhone(), user.getId());
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "user", key = "#userId"),
            @CacheEvict(value = "users", allEntries = true)
    })
    public void delete(Long userId, DeletePhoneRequestDto deleteEmailRequestDto) {

        String phone = deleteEmailRequestDto.getPhone();

        PhoneData phoneData = phoneDataRepository.findByUserIdAndPhone(userId, phone).orElseThrow(() -> {
            log.error("Phone {} was not found", phone);
            throw new EmailNotFoundException(String.format("Phone %s was not found", phone));
        });

        phoneDataRepository.delete(phoneData);
        log.info("Phone {} was successfully deleted", phone);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "user", key = "#userId"),
            @CacheEvict(value = "users", allEntries = true)
    })
    public void replace(Long userId, ReplacePhoneRequestDto replacePhoneRequestDto) {
        String oldPhone = replacePhoneRequestDto.getOldPhone();
        String newPhone = replacePhoneRequestDto.getNewPhone();

        PhoneData phoneData = phoneDataRepository.findByUserIdAndPhone(userId, oldPhone).orElseThrow(() -> {
            log.error("Phone {} was not found", oldPhone);
            throw new EmailNotFoundException(String.format("Phone %s was not found", oldPhone));
        });

        if(phoneDataRepository.existsByPhone(newPhone)) {
            log.error("New phone {} already exist", newPhone);
            throw new EmailAlreadyExistException(String.format("New phone %s already exist", newPhone));
        }

        phoneData.setPhone(newPhone);
        phoneDataRepository.save(phoneData);
        log.info("Phone {} was successfully replaced on {}", oldPhone, newPhone);
    }
}
