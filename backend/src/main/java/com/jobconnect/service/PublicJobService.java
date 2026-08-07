package com.jobconnect.service;

import com.jobconnect.dto.JobResponse;
import com.jobconnect.entity.Job;
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
     * Retrieves all available job postings.
     *
     * @return list of JobResponse containing all job postings
     */
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
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
                .orElseThrow(() -> new RuntimeException("Job not found."));

        return mapToResponse(job);
    }

    /**
     * Searches for job postings whose title contains the given keyword, case-insensitive.
     *
     * @param title the keyword to search for in job titles
     * @return list of JobResponse matching the title keyword
     */
    public List<JobResponse> searchByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Searches for job postings whose location contains the given keyword, case-insensitive.
     *
     * @param location the keyword to search for in job locations
     * @return list of JobResponse matching the location keyword
     */
    public List<JobResponse> searchByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location)
                .stream()
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
