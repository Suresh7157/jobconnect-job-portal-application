package com.jobconnect.repository;

import com.jobconnect.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RecruiterProfileRepository
 * Spring Data JPA Repository for RecruiterProfile persistence operations.
 */
@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {

    /**
     * Finds a RecruiterProfile by associated User ID.
     */
    Optional<RecruiterProfile> findByUserId(Long userId);
}
