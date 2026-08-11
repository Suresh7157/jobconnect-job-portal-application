package com.jobconnect.repository;

import com.jobconnect.entity.Job;
import com.jobconnect.enums.JobStatus;
import com.jobconnect.enums.JobType;
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
     * Finds all OPEN Jobs whose title contains the given keyword, case-insensitive.
     */
    List<Job> findByStatusAndTitleContainingIgnoreCase(JobStatus status, String title);

    /**
     * Finds all OPEN Jobs whose title contains the given keyword, case-insensitive, filtered by job type.
     */
    List<Job> findByStatusAndTitleContainingIgnoreCaseAndJobType(JobStatus status, String title, JobType jobType);

    /**
     * Finds all OPEN Jobs whose location contains the given keyword, case-insensitive.
     */
    List<Job> findByStatusAndLocationContainingIgnoreCase(JobStatus status, String location);

    /**
     * Finds all OPEN Jobs whose location contains the given keyword, case-insensitive, filtered by job type.
     */
    List<Job> findByStatusAndLocationContainingIgnoreCaseAndJobType(JobStatus status, String location, JobType jobType);

    /**
     * Finds all Jobs with a given status.
     */
    List<Job> findByStatus(JobStatus status);

    /**
     * Finds all Jobs with a given status and job type.
     */
    List<Job> findByStatusAndJobType(JobStatus status, JobType jobType);

    /**
     * Counts all Jobs belonging to a specific recruiter profile ID.
     */
    long countByRecruiterProfileId(Long recruiterProfileId);

    /**
     * Counts all Jobs belonging to a specific recruiter profile ID with a given status.
     */
    long countByRecruiterProfileIdAndStatus(Long recruiterProfileId, JobStatus status);
}
