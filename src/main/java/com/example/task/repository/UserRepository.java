package com.example.task.repository;

import com.example.task.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u JOIN u.emailData e WHERE e.email = :email")
    @EntityGraph(attributePaths = {"account", "phoneData", "emailData"})
    Optional<User> findByEmail(@Param("email") String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    @EntityGraph(attributePaths = {"account", "phoneData", "emailData"})
    Optional<User> findByIdForUpdate(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {"account", "phoneData", "emailData"})
    List<User> findAll();

    @Override
    @EntityGraph(attributePaths = {"account", "phoneData", "emailData"})
    Optional<User> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"account", "phoneData", "emailData"})
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
