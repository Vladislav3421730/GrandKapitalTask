package com.example.task.repository;

import com.example.task.model.EmailData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {


    Optional<EmailData> findByUserIdAndEmail(Long id, String email);

    boolean existsByEmail(String email);
}
