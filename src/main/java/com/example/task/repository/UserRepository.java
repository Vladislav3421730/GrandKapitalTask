package com.example.task.repository;

import com.example.task.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u JOIN u.emailData e WHERE e.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Override
    @EntityGraph(attributePaths = {"account", "phoneData", "emailData"})
    List<User> findAll();

    @Override
    @EntityGraph(attributePaths = {"account", "phoneData", "emailData"})
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
