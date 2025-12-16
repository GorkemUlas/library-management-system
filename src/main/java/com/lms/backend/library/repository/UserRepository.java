package com.lms.backend.library.repository;

import com.lms.backend.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    long count();

    Optional<User> findByEmail(String email);
}
