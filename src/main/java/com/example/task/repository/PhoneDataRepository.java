package com.example.task.repository;

import com.example.task.model.PhoneData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneDataRepository extends JpaRepository<PhoneData,Long> {

    Optional<PhoneData> findByUserIdAndPhone(Long id, String phone);

    boolean existsByPhone(String phone);
}
