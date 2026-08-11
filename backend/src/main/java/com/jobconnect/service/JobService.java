package com.jobconnect.service;

import com.jobconnect.dto.JobRequest;
import com.jobconnect.dto.JobResponse;
import com.jobconnect.dto.UpdateJobStatusRequest;
import com.jobconnect.entity.Job;
import com.jobconnect.entity.RecruiterProfile;
import com.jobconnect.exception.ResourceNotFoundException;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.repository.RecruiterProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JobService
 * Business service handling job posting creation, retrieval, update, and deletion operations.
 */
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    /**
     * Creates a new job posting for the recruiter identified by the given user ID.
     *
     * @param userId  the ID of the authenticated recruiter user
     * @param request the job posting data
     * @return JobResponse containing the created job details
     * @throws RuntimeException if no recruiter profile is found for the given user ID
     */
    @Transactional
    public JobResponse createJob(Long userId, JobRequest request) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found."));

        Job job = Job.builder()
                .recruiterProfile(recruiterProfile)
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .jobType(request.getJobType())
                .salary(request.getSalary())
                .build();

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    /**
     * Retrieves all job postings belonging to the recruiter identified by the given user ID.
     *
     * @param userId the ID of the authenticated recruiter user
     * @return list of JobResponse containing the recruiter's job postings
     * @throws RuntimeException if no recruiter profile is found for the given user ID
     */
    public List<JobResponse> getMyJobs(Long userId) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found."));

        return jobRepository.findByRecruiterProfileId(recruiterProfile.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing job posting owned by the recruiter identified by the given user ID.
     *
     * @param userId  the ID of the authenticated recruiter user
     * @param jobId   the ID of the job to update
     * @param request the updated job data
     * @return JobResponse containing the updated job details
     * @throws RuntimeException if recruiter profile, job is not found, or ownership check fails
     */
    @Transactional
    public JobResponse updateJob(Long userId, Long jobId, JobRequest request) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found."));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found."));

        if (!job.getRecruiterProfile().getId().equals(recruiterProfile.getId())) {
            throw new RuntimeException("You are not authorized to update this job.");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setSalary(request.getSalary());

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    /**
     * Deletes a job posting owned by the recruiter identified by the given user ID.
     *
     * @param userId the ID of the authenticated recruiter user
     * @param jobId  the ID of the job to delete
     * @throws RuntimeException if recruiter profile, job is not found, or ownership check fails
     */
    @Transactional
    public void deleteJob(Long userId, Long jobId) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found."));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found."));

        if (!job.getRecruiterProfile().getId().equals(recruiterProfile.getId())) {
            throw new RuntimeException("You are not authorized to delete this job.");
        }

        jobRepository.delete(job);
    }

    /**
     * Updates the status of a job posting owned by the recruiter identified by the given user ID.
     *
     * @param userId  the ID of the authenticated recruiter user
     * @param jobId   the ID of the job to update
     * @param request the job status update data
     * @return JobResponse containing the updated job details
     * @throws RuntimeException if recruiter profile, job is not found, or ownership check fails
     */
    @Transactional
    public JobResponse updateJobStatus(Long userId, Long jobId, UpdateJobStatusRequest request) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found."));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found."));

        if (!job.getRecruiterProfile().getId().equals(recruiterProfile.getId())) {
            throw new RuntimeException("You are not authorized to update this job.");
        }

        job.setStatus(request.getStatus());

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
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
