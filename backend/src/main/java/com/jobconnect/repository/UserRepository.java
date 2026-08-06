package com.jobconnect.repository;

import com.jobconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 * Spring Data JPA Repository for User persistence operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User entity by email address.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a User already exists with the given email address.
     */
    boolean existsByEmail(String email);
}
