package com.jobconnect.repository;

import com.jobconnect.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ApplicationRepository
 * Spring Data JPA Repository for Application persistence operations.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    /**
     * Finds an Application by candidate profile ID and job ID.
     */
    Optional<Application> findByCandidateProfileIdAndJobId(Long candidateProfileId, Long jobId);

    /**
     * Finds all Applications submitted by a specific candidate profile ID.
     */
    List<Application> findByCandidateProfileId(Long candidateProfileId);

    /**
     * Finds all Applications submitted for a specific job ID.
     */
    List<Application> findByJobId(Long jobId);
}
