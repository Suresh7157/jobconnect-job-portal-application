package com.jobconnect.repository;

import com.jobconnect.entity.Job;
import com.jobconnect.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JobRepository
 * Spring Data JPA Repository for Job persistence operations.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * Finds all Jobs belonging to a specific RecruiterProfile ID.
     */
    List<Job> findByRecruiterProfileId(Long recruiterProfileId);

    /**
     * Finds all Jobs whose title contains the given keyword, case-insensitive.
     */
    List<Job> findByTitleContainingIgnoreCase(String title);

    /**
     * Finds all Jobs whose location contains the given keyword, case-insensitive.
     */
    List<Job> findByLocationContainingIgnoreCase(String location);

    /**
     * Counts all Jobs belonging to a specific recruiter profile ID.
     */
    long countByRecruiterProfileId(Long recruiterProfileId);

    /**
     * Counts all Jobs belonging to a specific recruiter profile ID with a given status.
     */
    long countByRecruiterProfileIdAndStatus(Long recruiterProfileId, JobStatus status);
}
