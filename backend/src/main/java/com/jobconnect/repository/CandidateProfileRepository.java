package com.jobconnect.repository;

import com.jobconnect.entity.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CandidateProfileRepository
 * Spring Data JPA Repository for CandidateProfile persistence operations.
 */
@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {

    /**
     * Finds a CandidateProfile by associated User ID.
     */
    Optional<CandidateProfile> findByUserId(Long userId);
}
