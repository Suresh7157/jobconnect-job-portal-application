package com.jobconnect.service;

import com.jobconnect.dto.JobResponse;
import com.jobconnect.entity.Job;
import com.jobconnect.enums.JobStatus;
import com.jobconnect.enums.JobType;
import com.jobconnect.exception.ResourceNotFoundException;
import com.jobconnect.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PublicJobService
 * Business service handling publicly accessible job browsing and search operations.
 */
@Service
@RequiredArgsConstructor
public class PublicJobService {

    private final JobRepository jobRepository;

    /**
     * Retrieves all OPEN job postings, optionally filtered by job type.
     *
     * @param jobType optional job type filter; if null, all OPEN jobs are returned
     * @return list of JobResponse containing matching OPEN job postings
     */
    public List<JobResponse> getAllJobs(JobType jobType) {
        List<Job> jobs = (jobType == null)
                ? jobRepository.findByStatus(JobStatus.OPEN)
                : jobRepository.findByStatusAndJobType(JobStatus.OPEN, jobType);
        return jobs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single job posting by its ID.
     *
     * @param jobId the ID of the job
     * @return JobResponse containing the job details
     * @throws RuntimeException if no job is found for the given ID
     */
    public JobResponse getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found."));

        return mapToResponse(job);
    }

    /**
     * Searches for OPEN job postings whose title contains the given keyword, case-insensitive,
     * optionally filtered by job type.
     *
     * @param title   the keyword to search for in job titles
     * @param jobType optional job type filter; if null, all matching OPEN jobs are returned
     * @return list of JobResponse matching the criteria
     */
    public List<JobResponse> searchByTitle(String title, JobType jobType) {
        List<Job> jobs = (jobType == null)
                ? jobRepository.findByStatusAndTitleContainingIgnoreCase(JobStatus.OPEN, title)
                : jobRepository.findByStatusAndTitleContainingIgnoreCaseAndJobType(JobStatus.OPEN, title, jobType);
        return jobs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Searches for OPEN job postings whose location contains the given keyword, case-insensitive,
     * optionally filtered by job type.
     *
     * @param location the keyword to search for in job locations
     * @param jobType  optional job type filter; if null, all matching OPEN jobs are returned
     * @return list of JobResponse matching the criteria
     */
    public List<JobResponse> searchByLocation(String location, JobType jobType) {
        List<Job> jobs = (jobType == null)
                ? jobRepository.findByStatusAndLocationContainingIgnoreCase(JobStatus.OPEN, location)
                : jobRepository.findByStatusAndLocationContainingIgnoreCaseAndJobType(JobStatus.OPEN, location, jobType);
        return jobs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Maps a Job entity to a JobResponse DTO.
     *
     * @param job the Job entity
     * @return the mapped JobResponse
     */
    private JobResponse mapToResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .jobType(job.getJobType())
                .salary(job.getSalary())
                .status(job.getStatus())
                .companyName(job.getRecruiterProfile().getCompanyName())
                .createdAt(job.getCreatedAt())
                .build();
    }
}
